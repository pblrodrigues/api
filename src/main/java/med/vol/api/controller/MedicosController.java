package med.vol.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.vol.api.medico.DadosCadastroMedicos;
import med.vol.api.medico.Medico;
import med.vol.api.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicosController {
    
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedicos medico) {
         repository.save(new Medico(medico));
}
}
