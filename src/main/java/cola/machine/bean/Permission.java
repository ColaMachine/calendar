package cola.machine.bean;

public class Permission {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_permission.id
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_permission.code
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_permission.name
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_permission.pid
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    private String pid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_permission.orderBy
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    private String orderBy;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_permission.id
     *
     * @return the value of t_permission.id
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_permission.id
     *
     * @param id the value for t_permission.id
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_permission.code
     *
     * @return the value of t_permission.code
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_permission.code
     *
     * @param code the value for t_permission.code
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_permission.name
     *
     * @return the value of t_permission.name
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_permission.name
     *
     * @param name the value for t_permission.name
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_permission.pid
     *
     * @return the value of t_permission.pid
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    public String getPid() {
        return pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_permission.pid
     *
     * @param pid the value for t_permission.pid
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_permission.orderBy
     *
     * @return the value of t_permission.orderBy
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_permission.orderBy
     *
     * @param orderBy the value for t_permission.orderBy
     *
     * @mbggenerated Sat Dec 12 10:26:59 CST 2015
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy == null ? null : orderBy.trim();
    }
}