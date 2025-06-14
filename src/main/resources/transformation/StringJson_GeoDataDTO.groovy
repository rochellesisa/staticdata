import groovy.json.JsonSlurper
import com.ro.staticdata.domain.dto.GeoDataDTO


def convertToCountry(){
	def countries = new JsonSlurper().parseText(forConvert)
	def geoList = []
	countries.each {
		def geo = new GeoDataDTO(
			it.name.common,  
			it.capital?.getAt(0) ?: null, 
			it.region,
			null)
	    geoList.add(geo);
	}
	geoList;
}

def convertToCity(){
	def countries = new JsonSlurper().parseText(forConvert)
	def geoList = []
	countries.data.each { country ->
		country.cities.each { city ->
			def geo = new GeoDataDTO(city, null, country.country, null)
	    	geoList.add(geo);
		}
	}
	
	geoList;
}

if (flag == "country")
	return convertToCountry()
else if (flag == "city")
	return convertToCity()

return null;