package med.vol.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.vol.api.medico.DadosAtualizacaoMedicos;
import med.vol.api.medico.DadosCadastroMedicos;
import med.vol.api.medico.DadosListagemMedicos;
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
    
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedicos dados) {
         var medico = repository.getReferenceById(dados.id());
         medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
         //repository.deleteById(id); delete físico
         var medico = repository.getReferenceById(id);
         medico.excluir(); // delete lógico
    }

    //parametros
    //?sort=nome,asc&size=10&page=10
    @GetMapping
    public Page<DadosListagemMedicos> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemMedicos::new);
    }
}
