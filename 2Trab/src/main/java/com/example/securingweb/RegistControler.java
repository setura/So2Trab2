package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistControler {

    private RegistoRepository registoRepository;
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    public RegistControler(RegistoRepository registoRepository,UtilizadorRepository utilizadorRepository){
        this.registoRepository=registoRepository;
        this.utilizadorRepository=utilizadorRepository;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println("USER:"+ currentPrincipalName);//erro aqui devolve NULL
        long currentPrincipalID = utilizadorRepository.findByuserName(currentPrincipalName).getUserId();
        int typeValue=Integer.parseInt(value);
        Double longitude=Double.parseDouble(lon);
        Double latitude=Double.parseDouble(lat);
        registoRepository.save(new Registo(currentPrincipalID,typeValue, longitude,latitude, name));

        return "<a href=\"javascript:history.go(-2)\">Go Back</a>";

    }

    @GetMapping("/userReg")
    public Registo getUserRegist() {
        //return registoRepository.findAllByByuserId((long)2);
        return registoRepository.findByuserId((long)2);
    }

    @RequestMapping(value="/userPanel/delete",method = RequestMethod.POST)
    public String deleteUser(@RequestParam(name = "idDelete") String id) {
        long idParsed=Long.parseLong(id);
        registoRepository.deleteById(idParsed);
        return "<a href=\"javascript:history.back()\">Go Back</a>";
    }



}
