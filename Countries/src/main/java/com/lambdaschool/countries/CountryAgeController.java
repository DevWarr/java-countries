package com.lambdaschool.countries;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;


@RestController
@RequestMapping("/age")
public class CountryAgeController
{
    //localhost:8080/age/age/{age}
    @GetMapping(value = "/age/{age}", produces = {"application/json"})
    public ResponseEntity<?> listByAgeOrGreater(@PathVariable String age)
    {
        // Look in CountryNameController.java for why I have this try/catch statement, and the extra int "test" down below!
        long number;
        try
        {
            number = Integer.parseInt(age);
        }
        catch (NumberFormatException e)
        {
            number = 999; // Larger than all median age values
        }

        long test = number;
        ArrayList<Country> rtnCountryList = CountriesApplication.nowCountryList.findAllCountry(c -> c.getMedianAge() >= test);
        return new ResponseEntity<>(rtnCountryList, HttpStatus.OK);
    }

    //localhost:8080/age/min
    @GetMapping(value = "/min", produces = {"application/json"})
    public ResponseEntity<?> listMinAge()
    {
        ArrayList<Country> rtnCountryList = CountriesApplication.nowCountryList.countryList;
        rtnCountryList.sort(Comparator.comparingInt(Country::getMedianAge));
        Country rtnCountry = rtnCountryList.get(0);
        return new ResponseEntity<>(rtnCountry, HttpStatus.OK);
    }

    //localhost:8080/age/max
    @GetMapping(value = "/max", produces = {"application/json"})
    public ResponseEntity<?> listMaxAge()
    {
        ArrayList<Country> rtnCountryList = CountriesApplication.nowCountryList.countryList;
        rtnCountryList.sort(Comparator.comparingInt(Country::getMedianAge).reversed());
        Country rtnCountry = rtnCountryList.get(0);
        return new ResponseEntity<>(rtnCountry, HttpStatus.OK);
    }

    //====================STRETCH=================//
    //localhost:8080/age/median
    @GetMapping(value = "/median", produces = {"application/json"})
    public ResponseEntity<?> listMedianAge()
    {
        ArrayList<Country> rtnCountryList = CountriesApplication.nowCountryList.countryList;
        rtnCountryList.sort(Comparator.comparingInt(Country::getMedianAge));
        Country rtnCountry = rtnCountryList.get(rtnCountryList.size()/2);
        return new ResponseEntity<>(rtnCountry, HttpStatus.OK);
    }
}
