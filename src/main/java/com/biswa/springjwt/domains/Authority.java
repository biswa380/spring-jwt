package com.biswa.springjwt.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Created by fan.jin on 2016-11-03.
 */

@Entity
@Table(name="AUTHORITY")
public class Authority implements GrantedAuthority {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8367315470742232250L;

	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="name")
    String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Authority(String name) {
		super();
		this.name = name;
	}

	public Authority() {
		super();
		// TODO Auto-generated constructor stub
	}

}
