package com.project.splitit.util;
/**
 * <tt>AuthorityUtil</tt> contains all static <tt>Authority</tt>. It mainly used
 * with @Secured annotation. so who ever has that authority can access the code.
 * <p>
 *
 * Add here whenever we added a new authority for user.
 * <p>
 *
 *
 * @author yatin
 *
 */
public final class AuthorityUtils {

    /**
     * AUTHORITY TO FETCH AUTHORITY
     */
    public static final String AUTHORITY_FETCH = "ROLE_AUTHORITY_FETCH";

    /**
     * AUTHORITY TO FETCH ROLE
     */
    public static final String ROLE_FETCH = "ROLE_ROLE_FETCH";

    /**
     * AUTHORITY TO CREATE ROLE
     */
    public static final String ROLE_CREATE = "ROLE_ROLE_CREATE";

    /**
     * AUTHORITY TO UPDATE ROLE
     */
    public static final String ROLE_UPDATE = "ROLE_ROLE_UPDATE";

    /**
     * AUTHORITY TO DELETE ROLE
     */
    public static final String ROLE_DELETE = "ROLE_ROLE_DELETE";

    /**
     * AUTHORITY TO FETCH USER
     */
    public static final String USER_FETCH = "ROLE_USER_FETCH";

    /**
     * AUTHORITY TO FETCH PROJECT MANAGER
     */
    public static final String PROJECT_MANAGER_FETCH = "ROLE_PROJECT_MANAGER_FETCH";

    /**
     * AUTHORITY TO FETCH CUSTOMER
     */
    public static final String CUSTOMER_FETCH = "ROLE_CUSTOMER_FETCH";

    /**
     * AUTHORITY TO CREATE USER
     */
    public static final String USER_CREATE = "ROLE_USER_CREATE";

    /**
     * AUTHORITY TO UPDATE USER
     */
    public static final String USER_UPDATE = "ROLE_USER_UPDATE";

    /**
     * AUTHORITY TO DELETE USER
     */
    public static final String USER_DELETE = "ROLE_USER_DELETE";

    /**
     * AUTHORITY TO SEND EMAIL
     */
    public static final String SEND_EMAIL = "ROLE_SEND_EMAIL";


}
