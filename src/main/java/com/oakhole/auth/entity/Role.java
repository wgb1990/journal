package com.oakhole.auth.entity;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author Administrator
 * @since 14-3-17
 */
@SuppressWarnings("ALL")
@Entity
@Table(name="role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity{

    private String name;
    private String permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Transient
    public List<String> getPermissionList() {
        return ImmutableList.copyOf(StringUtils.split(permissions, ","));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
