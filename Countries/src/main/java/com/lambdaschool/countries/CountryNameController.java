package com.lambdaschool.countries;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/names")
public class CountryNameController
{

    //localhost:8080/names/all
    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> listAllNames()
    {
        ArrayList<Country> rtnCountryList = CountriesApplication.nowCountryList.countryList;
        rtnCountryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(rtnCountryList, HttpStatus.OK);
    }

    //localhost:8080/names/start/{letter}
    @GetMapping(value = "/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listNamesStartingWithString(@PathVariable String letter)
    {
        ArrayList<Country> rtnCountryList = CountriesApplication.nowCountryList.findAllCountry(c -> c.getName().toUpperCase().startsWith(letter.toUpperCase()));
        rtnCountryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(rtnCountryList, HttpStatus.OK);
    }

    //localhost:8080/names/size/{number}
    @GetMapping(value = "/size/{length}", produces = {"application/json"})
    public ResponseEntity<?> listNamesByNameLength(@PathVariable String length)
    {
        // right now at least, we could type in anything after "/size".
        // Maybe "/size/twelve" or "/size/askjhl89y23".
        // We don't want an error, so...
        // We take in {length} as a String and parseInt(length)
        // If length isn't an int, then we'll catch an error, and set number = 999
        //     (won't match with any name lengths, and will return an empty array)
        int number;
        try
        {
            number = Integer.parseInt(length);
        }
        catch (NumberFormatException e)
        {
            number = 999;
        }

        // Lambda Expressions only take final values. Because "number" has variable values, it can't be used.
        // So, I just create number, then change it, then assign it to a final value. Not efficient... but it works.
        int test = number;
        ArrayList<Country> rtnCountryList = CountriesApplication.nowCountryList.findAllCountry(c -> c.getName().length() >= test);
        rtnCountryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(rtnCountryList, HttpStatus.OK);
    }
}
