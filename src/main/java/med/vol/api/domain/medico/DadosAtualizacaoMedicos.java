package med.vol.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.vol.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedicos(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}