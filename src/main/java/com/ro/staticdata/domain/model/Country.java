package com.ro.staticdata.domain.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "geo_country")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country extends JPAEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "capital")
	private String capital;

	@ManyToOne
	@JoinColumn(name = "region_id")
	private Region region;

	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<City> cities;

	public Country(String name, String capital, Region region) {
		super();
		this.name = name;
		this.capital = capital;
		this.region = region;
	}

	@Override
	public int hashCode() {
		return Objects.hash(capital, name, region.getId(), getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		return Objects.equals(capital, other.capital) && Objects.equals(name, other.name)
				&& Objects.equals(region.getId(), other.region.getId());
	}


	
}
