package com.dozenx.web.module.merchant.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商户信息
 */
public class CenterPubMerchant implements Serializable {
    private static final long serialVersionUID = 350504050668708L;

    /**
     * center_pub_merchant.id: 
     * <p>
     * <code>
     * 主键编号<br>
     * </code>
     */
    private Long id;

    /**
     * center_pub_merchant.parentId: 
     * <p>
     * <code>
     * 父编号<br>
     * </code>
     */
    private Long parentid;

    /**
     * center_pub_merchant.account_id: 
     * <p>
     * <code>
     * 商户管理员账号<br>
     * </code>
     */
    private Long accountId;

    /**
     * center_pub_merchant.merchant_name: 
     * <p>
     * <code>
     * 商户名称<br>
     * </code>
     */
    private String merchantName;

    /**
     * center_pub_merchant.full_name: 
     * <p>
     * <code>
     * 商户全名<br>
     * </code>
     */
    private String fullName;

    /**
     * center_pub_merchant.telephone: 
     * <p>
     * <code>
     * 手机号码<br>
     * </code>
     */
    private String telephone;

    /**
     * center_pub_merchant.email: 
     * <p>
     * <code>
     * 邮箱<br>
     * </code>
     */
    private String email;

    /**
     * center_pub_merchant.thumb: 
     * <p>
     * <code>
     * 商户图标<br>
     * </code>
     */
    private String thumb;

    /**
     * center_pub_merchant.industry: 
     * <p>
     * <code>
     * 行业编号<br>
     * </code>
     */
    private String industry;

    /**
     * center_pub_merchant.user_id: 
     * <p>
     * <code>
     * 关系用户编号<br>
     * </code>
     */
    private Long userId;

    /**
     * center_pub_merchant.province: 
     * <p>
     * <code>
     * 省<br>
     * </code>
     */
    private Integer province;

    /**
     * center_pub_merchant.city: 
     * <p>
     * <code>
     * 市<br>
     * </code>
     */
    private Integer city;

    /**
     * center_pub_merchant.county: 
     * <p>
     * <code>
     * 县<br>
     * </code>
     */
    private Integer county;

    /**
     * center_pub_merchant.town: 
     * <p>
     * <code>
     * 镇<br>
     * </code>
     */
    private Integer town;

    /**
     * center_pub_merchant.village: 
     * <p>
     * <code>
     * 村<br>
     * </code>
     */
    private Integer village;

    /**
     * center_pub_merchant.address: 
     * <p>
     * <code>
     * 详细地址<br>
     * </code>
     */
    private String address;

    /**
     * center_pub_merchant.open_time: 
     * <p>
     * <code>
     * 营业开门时间<br>
     * </code>
     */
    private String openTime;

    /**
     * center_pub_merchant.close_time: 
     * <p>
     * <code>
     * 营业关门时间<br>
     * </code>
     */
    private String closeTime;

    /**
     * center_pub_merchant.status: 
     * <p>
     * <code>
     * 状态 1：正常；9：注销<br>
     * </code>
     */
    private Integer status;

    /**
     * center_pub_merchant.status_date: 
     * <p>
     * <code>
     * 状态时间<br>
     * </code>
     */
    private Date statusDate;

    /**
     * center_pub_merchant.remarks: 
     * <p>
     * <code>
     * 描述<br>
     * </code>
     */
    private String remarks;

    /**
     * center_pub_merchant.create_date: 
     * <p>
     * <code>
     * 创建时间<br>
     * </code>
     */
    private Date createDate;

    /**
     * center_pub_merchant.modify_date: 
     * <p>
     * <code>
     * 修改时间<br>
     * </code>
     */
    private Date modifyDate;
    
    private List<CenterPubMerchant> subMerchantList;
    
    private String style;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb == null ? null : thumb.trim();
    }

    public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getCounty() {
        return county;
    }

    public void setCounty(Integer county) {
        this.county = county;
    }

    public Integer getTown() {
        return town;
    }

    public void setTown(Integer town) {
        this.town = town;
    }

    public Integer getVillage() {
        return village;
    }

    public void setVillage(Integer village) {
        this.village = village;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

	public List<CenterPubMerchant> getSubMerchantList() {
		return subMerchantList;
	}

	public void setSubMerchantList(List<CenterPubMerchant> subMerchantList) {
		this.subMerchantList = subMerchantList;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

    public void setIsError(short s) {
        // TODO Auto-generated method stub
        
    }

}