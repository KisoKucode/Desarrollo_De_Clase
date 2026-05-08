package model.repository;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import model.ProgramaAcademico;
import model.Usuario;

public class FileUsuarioRepository implements IUsuarioRepository {

    private final String FILE_NAME = "usuarios.txt";

    @Override
    public void save(Usuario u) throws IOException {
        String linea = String.join("|",
                u.nombre(),
                u.dni(),
                u.email(),
                u.fechaNacimiento().toString(),
                u.programa().name()
        );

        Files.writeString(Paths.get(FILE_NAME),
                linea + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    @Override
    public List<Usuario> findAll() throws IOException {
        Path path = Paths.get(FILE_NAME);
        if (!Files.exists(path)) return List.of();

        return Files.lines(path)
                .map(line -> {
                    String[] p = line.split("\\|");
                    return new Usuario(
                            p[0],
                            p[1],
                            p[2],
                            LocalDate.parse(p[3]),
                            ProgramaAcademico.valueOf(p[4])
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }
}