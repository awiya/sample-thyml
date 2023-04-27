package io.awiya.hopitalh2.web;


import io.awiya.hopitalh2.entity.Patient;
import io.awiya.hopitalh2.exceptions.PatientNotFoundException;
import io.awiya.hopitalh2.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        Page<Patient> pagePatients = patientRepository.findByNameContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping("/deletePatient")
    public String deletePatient(@RequestParam(name = "id") Long id,
                                String keyword,
                                int page) {
        patientRepository.deleteById(id);
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @PostMapping("/savePatient")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "formPatient";
    }

    @GetMapping("/editPatient")
    public String editPatient(@RequestParam(name = "id") Long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(()-> new PatientNotFoundException("The patient that was requested could not be located."));
        model.addAttribute("patient", patient);
        return "editPatient";
    }
}
