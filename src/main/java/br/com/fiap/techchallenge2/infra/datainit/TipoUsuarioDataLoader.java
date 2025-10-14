package br.com.fiap.techchallenge2.infra.datainit;

import br.com.fiap.techchallenge2.infra.model.TipoUsuarioModel;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.fiap.techchallenge2.infra.repository.TipoUsuarioRepository;

@Component
public class TipoUsuarioDataLoader {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @PostConstruct
    public void init() {
        if (tipoUsuarioRepository.count() == 0) {
//            TipoUsuarioModel tipoUsuario = new TipoUsuarioModel();
//            tipoUsuario.setNome("Admin");
//            tipoUsuarioRepository.save(tipoUsuario);
        }
    }
}