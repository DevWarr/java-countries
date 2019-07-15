package com.lambdaschool.countries;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/population")
public class CountryPopulationController
{

    //localhost:8080/population/size/{population}
    @GetMapping(value = "/size/{population}", produces = {"application/json"})
    public ResponseEntity<?> listByPopulationOrGreater(@PathVariable String population)
    {
        // Look in CountryNameController.java for why I have this try/catch statement, and the extra int "test" down below!
        long number;
        try
        {
            number = Integer.parseInt(population);
        }
        catch (NumberFormatException e)
        {
            number = 1999999999; // Larger than all other population values
        }

        long test = number;
        ArrayList<Country> rtnCountryList = CountriesApplication.nowCountryList.findAllCountry(c -> c.getPopulation() >= test);
        return new ResponseEntity<>(rtnCountryList, HttpStatus.OK);
    }

    //localhost:8080/population/min
    @GetMapping(value = "/min", produces = {"application/json"})
    public ResponseEntity<?> listMinPopulation()
    {
        ArrayList<Country> rtnCountryList = CountriesApplication.nowCountryList.countryList;
        rtnCountryList.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));
        Country rtnCountry = rtnCountryList.get(0);
        return new ResponseEntity<>(rtnCountry, HttpStatus.OK);
    }

    //localhost:8080/population/max
    @GetMapping(value = "/max", produces = {"application/json"})
    public ResponseEntity<?> listMaxPopulation()
    {
        ArrayList<Country> rtnCountryList = CountriesApplication.nowCountryList.countryList;
        rtnCountryList.sort((c1, c2) -> (int)(c2.getPopulation() - c1.getPopulation()));
        Country rtnCountry = rtnCountryList.get(0);
        return new ResponseEntity<>(rtnCountry, HttpStatus.OK);
    }
}
