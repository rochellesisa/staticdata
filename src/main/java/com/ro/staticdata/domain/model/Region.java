package com.ro.staticdata.domain.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "geo_region")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Region extends JPAEntity {

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "region", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Country> countries;

	public Region(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(countries, name, getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Region other = (Region) obj;
		return Objects.equals(getId(), other.getId()) && Objects.equals(name, other.name);
	}
	
	

}
