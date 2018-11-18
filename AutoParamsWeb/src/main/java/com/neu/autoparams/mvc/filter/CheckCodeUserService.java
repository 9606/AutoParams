package com.neu.autoparams.mvc.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class CheckCodeUserService implements UserDetailsService {

    @Resource
    JdbcTemplate jdbcTemplate;

    protected final Log logger = LogFactory.getLog(this.getClass());
    protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private String usersByUsernameQuery = "SELECT username,password,enabled FROM users WHERE username = ?";
    private String usersByEmailQuery = "SELECT username,password,enabled FROM users WHERE email = ?";
    private String usersByTelQuery = "SELECT username,password,enabled FROM users WHERE telephone = ?";

    private String authoritiesByUsernameQuery = "SELECT a.username, a.p_name FROM (SELECT DISTINCT a.username, e.p_name FROM users a, " +
            "user_role_rela b,roles c, role_perm_rela d, permission e " +
            "WHERE a.u_id = b.u_id AND b.r_id = c.r_id AND b.r_id = d.r_id " +
            "AND d.p_id = e.p_id AND a.username= ?) a;";

    private boolean usernameBasedPrimaryKey = true;
    private boolean enableAuthorities = true;
    private boolean enableGroups;
    private final String telPattern = "^[1][0-9]{10}$";
    private static final String emailPattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public CheckCodeUserService() {
    }

    @Override
    public UserDetails loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {
        List<UserDetails> users;
        if (Pattern.matches(telPattern, userIdentifier)){
            users = this.loadUsers(userIdentifier, usersByTelQuery);
            if (users.size() == 0) {
                this.logger.debug("Query returned no results for user '" + userIdentifier + "'");
                throw new UserTelNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound", new Object[]{userIdentifier}, "UserTelephone {0} not found"), userIdentifier);
            }
        }else if(Pattern.matches(emailPattern, userIdentifier)){
            users = this.loadUsers(userIdentifier, usersByEmailQuery);
            if (users.size() == 0) {
                this.logger.debug("Query returned no results for user '" + userIdentifier + "'");
                throw new UserEmailNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound", new Object[]{userIdentifier}, "UserEmail {0} not found"), userIdentifier);
            }
        }else {
            users = this.loadUsers(userIdentifier, usersByUsernameQuery);
            if (users.size() == 0) {
                this.logger.debug("Query returned no results for user '" + userIdentifier + "'");
                throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.notFound", new Object[]{userIdentifier}, "Username {0} not found"), userIdentifier);
            }
        }
        UserDetails user = (UserDetails)users.get(0);
        Set<GrantedAuthority> dbAuthsSet = new HashSet();
        if (this.enableAuthorities) {
            dbAuthsSet.addAll(this.loadUserAuthorities(user.getUsername()));
        }

        List<GrantedAuthority> dbAuths = new ArrayList(dbAuthsSet);
        if (dbAuths.size() == 0) {
            this.logger.debug("User '" + userIdentifier + "' has no authorities and will be treated as 'not found'");
            throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.noAuthority", new Object[]{userIdentifier}, "User {0} has no GrantedAuthority"), userIdentifier);
        } else {
            return this.createUserDetails(user.getUsername(), user, dbAuths);
        }
    }

    protected List<UserDetails> loadUsers(String userIdentifier, String query) {
        return jdbcTemplate.query(query, new String[]{userIdentifier}, (rs, rowNum) -> {
            String username = rs.getString(1);
            String password = rs.getString(2);
            boolean enabled = rs.getBoolean(3);
            return new User(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
        });
    }

    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        return jdbcTemplate.query(this.authoritiesByUsernameQuery, new String[]{username}, new RowMapper<GrantedAuthority>() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String roleName = rs.getString(2);
                return new SimpleGrantedAuthority(roleName);
            }
        });
    }

    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();
        if (!this.usernameBasedPrimaryKey) {
            returnUsername = username;
        }

        return new User(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), true, true, true, combinedAuthorities);
    }

}
