package med.vol.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.vol.api.domain.medico.DadosAtualizacaoMedicos;
import med.vol.api.domain.medico.DadosCadastroMedicos;
import med.vol.api.domain.medico.DadosDetalhamentoMedico;
import med.vol.api.domain.medico.DadosListagemMedicos;
import med.vol.api.domain.medico.Medico;
import med.vol.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicosController {

     @Autowired
     private MedicoRepository repository;

     @PostMapping
     @Transactional
     public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicos dados, UriComponentsBuilder uriBuilder) {
          var medico = new Medico(dados);
          repository.save(medico);

          var uri = uriBuilder.path("/medico/{id}").buildAndExpand(medico.getId()).toUri();

          return ResponseEntity.created(uri).body(medico);
     }

     @PutMapping
     @Transactional
     public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedicos dados) {
          var medico = repository.getReferenceById(dados.id());
          medico.atualizarInformacoes(dados);
          return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
     }

     @DeleteMapping("/{id}")
     @Transactional
     public ResponseEntity excluir(@PathVariable Long id) {
          // repository.deleteById(id); delete físico
          var medico = repository.getReferenceById(id);
          medico.excluir(); // delete lógico

          return ResponseEntity.noContent().build();
     }

     // parametros
     // ?sort=nome,asc&size=10&page=10
     @GetMapping
     public ResponseEntity<Page<DadosListagemMedicos>> listar(
               @PageableDefault(size = 10, sort = { "nome" }) Pageable pageable) {
          var page = repository.findAllByAtivoTrue(pageable).map(DadosListagemMedicos::new);
          return ResponseEntity.ok(page);
     }

     @GetMapping("/{id}")
     //@Secured("ROLE_ADMIN")
     public ResponseEntity obter(@PathVariable Long id) {
          var medico = repository.getReferenceById(id);
          return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
     }
}
