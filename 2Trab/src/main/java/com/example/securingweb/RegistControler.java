package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistControler {

    private RegistoRepository registoRepository;

    @Autowired
    public RegistControler(RegistoRepository registoRepository){
        this.registoRepository=registoRepository;
    }
    @GetMapping("/locals")
    public Iterable<Registo> getLocals() {
       return registoRepository.findAll();

    }
    @RequestMapping(value="/locals/add",method = RequestMethod.POST)
    public String addLocal(@RequestParam(name = "localName") String name,
                         @RequestParam(name = "type") String value,
                        @RequestParam(name = "long") String lon,
                        @RequestParam(name = "lat") String lat)
    {
        int typeValue=Integer.parseInt(value);
        Double longitude=Double.parseDouble(lon);
        Double latitude=Double.parseDouble(lat);
        registoRepository.save(new Registo(1,typeValue, longitude,latitude, name));

        return "<a href=\"javascript:history.go(-2)\">Go Back</a>";

    }



}
