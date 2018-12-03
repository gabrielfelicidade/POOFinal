/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatec.poo.control;

import fatec.poo.model.Curso;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author Churras
 */
public class DaoCurso {

    private Connection conn;

    public DaoCurso(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Curso curso) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO CURSO(SIGLA, NOME, "
                    + "CARGAHORARIA, VALOR, DATAVIGENCIA, VALORHORAINSTRUTOR, PROGRAMA) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, curso.getSigla());
            ps.setString(2, curso.getNome());
            ps.setInt(3, curso.getCargaHoraria());
            ps.setDouble(4, curso.getValor());
            ps.setString(5, curso.getDataVigencia());
            ps.setDouble(6, curso.getValorHoraInstrutor());
            ps.setString(7, curso.getPrograma());

            ps.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void alterar(Curso curso) {
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement("UPDATE CURSO SET NOME = ?, CARGAHORARIA = ?, "
                    + "VALOR = ?, DATAVIGENCIA = ?, VALORHORAINSTRUTOR = ?, PROGRAMA = ? "
                    + "WHERE SIGLA = ?");

            ps.setString(1, curso.getNome());
            ps.setInt(2, curso.getCargaHoraria());
            ps.setDouble(3, curso.getValor());
            ps.setString(4, curso.getDataVigencia());
            ps.setDouble(5, curso.getValorHoraInstrutor());
            ps.setString(6, curso.getPrograma());
            ps.setString(7, curso.getSigla());

            ps.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public ArrayList<Curso> consultarCursos() {
        ArrayList<Curso> cursos = new ArrayList<>();
        Curso curso = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM CURSO");

            ResultSet rs = ps.executeQuery();

            while (rs.next() == true) {
                curso = new Curso(rs.getString("SIGLA"), rs.getString("NOME"));
                curso.setCargaHoraria(rs.getInt("CARGAHORARIA"));
                curso.setValor(rs.getDouble("VALOR"));
                curso.setDataVigencia(rs.getString("DATAVIGENCIA"));
                curso.setValorHoraInstrutor(rs.getDouble("VALORHORAINSTRUTOR"));
                curso.setPrograma(rs.getString("PROGRAMA"));

                cursos.add(curso);
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return (cursos);
    }

    public Curso consultar(String sigla) {
        Curso curso = null;

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM CURSO c "
                    + " WHERE c.SIGLA = ?");

            ps.setString(1, sigla);
            ResultSet rs = ps.executeQuery();

            if (rs.next() == true) {
                curso = new Curso(sigla, rs.getString("NOME"));
                curso.setCargaHoraria(rs.getInt("CARGAHORARIA"));
                curso.setValor(rs.getDouble("VALOR"));
                curso.setDataVigencia(rs.getString("DATAVIGENCIA"));
                curso.setValorHoraInstrutor(rs.getDouble("VALORHORAINSTRUTOR"));
                curso.setPrograma(rs.getString("PROGRAMA"));

            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return (curso);
    }

    public void excluir(Curso curso) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE TURMA SET SIGLACURSO = ? "
                    + "WHERE SIGLACURSO = ?");

            ps.setNull(1, Types.VARCHAR);
            ps.setString(2, curso.getSigla());

            ps.execute();

            ps = conn.prepareStatement("DELETE FROM CURSO WHERE SIGLA = ?");

            ps.setString(1, curso.getSigla());

            ps.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
