package com.example.Induztek.config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.Induztek.enumeration.RolUsuario;
import com.example.Induztek.model.Cliente;
import com.example.Induztek.model.Instalacion;
import com.example.Induztek.model.TipoEquipo;
import com.example.Induztek.model.Usuario;
import com.example.Induztek.repository.ClienteRepository;
import com.example.Induztek.repository.InstalacionRepository;
import com.example.Induztek.repository.TipoEquipoRepository;
import com.example.Induztek.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final InstalacionRepository instalacionRepository;
    private final TipoEquipoRepository tipoEquipoRepository;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           ClienteRepository clienteRepository,
                           InstalacionRepository instalacionRepository,
                           TipoEquipoRepository tipoEquipoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.instalacionRepository = instalacionRepository;
        this.tipoEquipoRepository = tipoEquipoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedUsuarios();
        seedClientesEInstalaciones();
    }

    private void seedUsuarios() {
        if (!usuarioRepository.existsByUsername("admin")) {
            usuarioRepository.save(new Usuario(
                    "usr-admin",
                    "admin",
                    "Administrador del sistema",
                    sha256("admin"),
                    RolUsuario.ADMINISTRADOR
            ));
        }

        if (!usuarioRepository.existsByUsername("user")) {
            usuarioRepository.save(new Usuario(
                    "usr-tech",
                    "user",
                    "Usuaruio de prueba",
                    sha256("user"),
                    RolUsuario.TECNICO
            ));
        }
    }

    private void seedClientesEInstalaciones() {
        if (clienteRepository.count() > 0) {
            return;
        }

        // Cliente 1: Jumbo (dos instalaciones; incluye Jumbo Costanera Center de README_2.md)
        Cliente jumbo = clienteRepository.save(new Cliente("cli-jumbo", "Jumbo"));

        Instalacion jumboCostanera = instalacionRepository.save(new Instalacion(
                "inst-jumbo-costanera",
                jumbo,
                "Jumbo Costanera Center",
                -33.4173,
                -70.6062
        ));
        Instalacion jumboMaipu = instalacionRepository.save(new Instalacion(
                "inst-jumbo-maipu",
                jumbo,
                "Jumbo Maipú",
                -33.5105,
                -70.7584
        ));

        // Cliente 2: Falabella (dos instalaciones con coordenadas GPS de prueba)
        Cliente falabella = clienteRepository.save(new Cliente("cli-falabella", "Falabella"));

        Instalacion falabellaParqueArauco = instalacionRepository.save(new Instalacion(
                "inst-falabella-parque-arauco",
                falabella,
                "Falabella Parque Arauco",
                -33.4021,
                -70.5756
        ));
        Instalacion falabellaPlazaEgaña = instalacionRepository.save(new Instalacion(
                "inst-falabella-plaza-egana",
                falabella,
                "Falabella Mall Plaza Egaña",
                -33.4526,
                -70.5681
        ));

        // Tipos de equipo de ejemplo para las listas dependientes de cada instalación
        List<Instalacion> todasInstalaciones = List.of(
                jumboCostanera, jumboMaipu, falabellaParqueArauco, falabellaPlazaEgaña
        );

        int idx = 1;
        for (Instalacion inst : todasInstalaciones) {
            tipoEquipoRepository.save(new TipoEquipo(
                    "tipo-eq-" + idx + "-1", inst, "Transformador"
            ));
            tipoEquipoRepository.save(new TipoEquipo(
                    "tipo-eq-" + idx + "-2", inst, "Tablero Eléctrico"
            ));
            tipoEquipoRepository.save(new TipoEquipo(
                    "tipo-eq-" + idx + "-3", inst, "Celda"
            ));
            idx++;
        }
    }

    private String sha256(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Error al calcular SHA-256", e);
        }
    }
}
