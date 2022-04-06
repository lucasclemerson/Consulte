package com.projeto.br.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.projeto.br.controller.Comorbidade;
import com.projeto.br.controller.Paciente;
import com.projeto.br.controller.Prontuario;
import com.projeto.br.controller.RelacionalComorbidadePaciente;
import com.projeto.br.controller.Usuario;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class BancoDados extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "Banco_consult_sqlite";

    //// PACIENTE
    public static final String TABELA_PACIENTE = "tb_paciente";
    public static final String CL_NOME = "nome";
    public static final String CL_CARTAO_SUS = "cartao_sus";
    public static final String CL_N_PRONTUARIO = "n_prontuario";
    public static final String CL_SEXO = "sexo";

    //// USUARIO
    public static final String TABELA_USUARIO = "tb_usuario";
    public static final String CL_NICKNAME = "nickname";
    public static final String CL_SENHA = "senha";
    public static final String CL_AVATAR = "avatar";

    //// PRONTUARIO
    public static final String TABELA_PRONTUARIO = "tb_prontuarios";
    public static final String CL_NUMERO = "numero";
    public static final String CL_ENDERECO = "endereco";

    //// COMORBIDADE
    public static final String TABELA_COMORBIDADE = "tb_comorbidade";
    public static final String CL_ID = "id";

    ///// RELACIONAL PACIENTE PRONTUARIO
    public static final String TABELA_PACIENTE_COMORBIDADE = "tb_relacional_pc";
    public static final String CL_CARTAO_PACIENTE = "cartao_paciente";
    public static final String CL_ID_COMORBIDADE = "id_comorbidade";




    public BancoDados(Context context) {
        super(context, NOME_BANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABELA_PACIENTE + "("
                + CL_NOME + " VARCHAR(100), "
                + CL_N_PRONTUARIO + " VARCHAR(100), "
                + CL_SEXO + " VARCHAR(100), "
                + CL_CARTAO_SUS + " VARCHAR(100) PRIMARY KEY)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + TABELA_USUARIO + "("
                + CL_NOME + " VARCHAR(100), "
                + CL_SENHA + " VARCHAR(100), "
                + CL_AVATAR + " VARCHAR(100), "
                + CL_NICKNAME + " VARCHAR(100) PRIMARY KEY)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + TABELA_PRONTUARIO + "("
                + CL_ENDERECO + " VARCHAR(100), "
                + CL_NUMERO + " VARCHAR(10) PRIMARY KEY)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + TABELA_COMORBIDADE + "("
                + CL_NOME + " VARCHAR(100), "
                + CL_ID + " INT PRIMARY KEY)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + TABELA_PACIENTE_COMORBIDADE + "("
                + CL_CARTAO_PACIENTE + " VARCHAR(100), "
                + CL_ID_COMORBIDADE + " VARCHAR(100), "
                + CL_ID + " INT PRIMARY KEY)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    ///// RELACIONAL PACIENTE PRONTUARIO
    public boolean inserir_paciente_comorbidade (Paciente p, Comorbidade c){
        try{
            SQLiteDatabase bd = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CL_CARTAO_PACIENTE, p.getCartao_sus());
            cv.put(CL_ID_COMORBIDADE, c.getId());

            bd.insert(TABELA_PACIENTE_COMORBIDADE, null, cv);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public void exemplo (Paciente p){
        HashMap<Paciente, Comorbidade> hash = listar_paciente_comorbidade();
        Collection<Comorbidade> lista = null;

        if (hash.containsKey(p)){
            lista = hash.values();
        }

        for (Comorbidade c: lista) {
            Log.e("COMORBIDADE: ", c.toString());
        }
    }

    public HashMap<Paciente,Comorbidade> listar_paciente_comorbidade(){
        SQLiteDatabase bd = getReadableDatabase();
        String [] colunas = {CL_CARTAO_PACIENTE, CL_ID_COMORBIDADE, CL_ID};

        Cursor cursor = bd.query(TABELA_PACIENTE_COMORBIDADE, colunas, null, null, null, null, null, null);

        ArrayList<Paciente> pacientes = listar_pacientes();
        ArrayList<Comorbidade> comorbidades = listar_comorbidade();
        HashMap<Paciente, Comorbidade> lista = new HashMap<>();


        while (cursor.moveToNext()){
            String cartao_paciente = cursor.getString(cursor.getColumnIndexOrThrow(CL_CARTAO_PACIENTE));
            int id_comorbidade = cursor.getInt(cursor.getColumnIndexOrThrow(CL_ID_COMORBIDADE));

            Paciente paciente = new Paciente();
            Comorbidade comorbidade = new Comorbidade();

            ///  paciente
            for (Paciente p : pacientes) {
                if (p.getCartao_sus().equals(cartao_paciente)){
                    paciente = p;
                }
            }
            // comorbidade
            for (Comorbidade c : comorbidades) {
                if (c.getId() == id_comorbidade){
                    comorbidade = c;
                }
            }
            // colocar no hashmap
            lista.put(paciente, comorbidade);
        }
        return lista;
    }

    public boolean delete_paciente_comorbidade (int id_comorbidade, String cartao_paciente){
        try {
            SQLiteDatabase bd = this.getWritableDatabase();
            bd.delete(TABELA_PACIENTE_COMORBIDADE, "id_comorbidade=? AND cartao_paciente=?", new String[]{Integer.toString(id_comorbidade), cartao_paciente});
            bd.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean update_paciente_comorbidade (RelacionalComorbidadePaciente tcp) {
        try{
            SQLiteDatabase bd = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CL_ID_COMORBIDADE, tcp.getId_comorbidade());

            bd.update(TABELA_PACIENTE_COMORBIDADE, values, "id=?", new String[]{Integer.toString(tcp.getId())});
            bd.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    ////////////////////// COMORBIDADE
    public boolean inserir_comorbidade (Comorbidade c){
        try{
            SQLiteDatabase bd = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CL_NOME, c.getNome());
            cv.put(CL_ID, c.getId());

            bd.insert(TABELA_COMORBIDADE, null, cv);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ArrayList<Comorbidade> listar_comorbidade(){
        SQLiteDatabase bd = getReadableDatabase();
        String [] colunas = {CL_NOME, CL_ID};

        Cursor cursor = bd.query(TABELA_COMORBIDADE, colunas, null, null, null, null, null, null);
        ArrayList<Comorbidade> lista = new ArrayList<>();

        while (cursor.moveToNext()){
            String nome = cursor.getString(cursor.getColumnIndexOrThrow(CL_NOME));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(CL_ID));
            lista.add(new Comorbidade(nome, id));
        }
        return lista;
    }

    public boolean delete_comorbidade (int id){
        try {
            SQLiteDatabase bd = this.getWritableDatabase();
            bd.delete(TABELA_COMORBIDADE, "id=?", new String[]{Integer.toString(id)});
            bd.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean update_comorbidade (Comorbidade c) {
        try{
            SQLiteDatabase bd = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CL_NOME, c.getNome());

            bd.update(TABELA_COMORBIDADE, values, "cartao_sus=?", new String[]{Integer.toString(c.getId())});
            bd.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    ////////////////////// PACIENTE
    public boolean inserir_paciente (Paciente p){
        try{
            SQLiteDatabase bd = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CL_NOME, p.getNome());
            cv.put(CL_CARTAO_SUS, p.getCartao_sus());
            cv.put(CL_N_PRONTUARIO, p.getN_prontuario());
            cv.put(CL_SEXO, p.getSexo());

            bd.insert(TABELA_PACIENTE, null, cv);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ArrayList<Paciente> listar_pacientes(){
        SQLiteDatabase bd = getReadableDatabase();
        String [] colunas = {CL_NOME, CL_CARTAO_SUS, CL_N_PRONTUARIO, CL_SEXO};

        Cursor cursor = bd.query(TABELA_PACIENTE, colunas, null, null, null, null, null, null);
        ArrayList<Paciente> lista = new ArrayList<>();

        while (cursor.moveToNext()){
            String nome = cursor.getString(cursor.getColumnIndexOrThrow(CL_NOME));
            String cartao_sus = cursor.getString(cursor.getColumnIndexOrThrow(CL_CARTAO_SUS));
            String n_prontuario = cursor.getString(cursor.getColumnIndexOrThrow(CL_N_PRONTUARIO));
            String sexo = cursor.getString(cursor.getColumnIndexOrThrow(CL_SEXO));
            lista.add(new Paciente(nome, cartao_sus, n_prontuario, sexo));
        }
        return lista;
    }

    public boolean delete_paciente (String cartao_sus){
        try {
            SQLiteDatabase bd = this.getWritableDatabase();
            bd.delete(TABELA_PACIENTE, "cartao_sus=?", new String[]{cartao_sus});
            bd.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean update_paciente (Paciente p) {
        try{
            SQLiteDatabase bd = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CL_NOME, p.getNome());
            values.put(CL_N_PRONTUARIO, p.getN_prontuario());
            values.put(CL_SEXO, p.getSexo());

            bd.update(TABELA_PACIENTE, values, "cartao_sus=?", new String[]{p.getCartao_sus()});
            bd.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    ////////////////////// PRONTUARIO
    public boolean inserir_prontuario (Prontuario p){
        try{
            SQLiteDatabase bd = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CL_ENDERECO, p.getEndereco());
            cv.put(CL_NUMERO, p.getNumero());

            bd.insert(TABELA_PRONTUARIO, null, cv);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ArrayList<Prontuario> listar_prontuarios(){
        SQLiteDatabase bd = getReadableDatabase();
        String [] colunas = {CL_NUMERO, CL_ENDERECO};
        Cursor cursor = bd.query(TABELA_PRONTUARIO, colunas, null, null, null, null, null, null);
        ArrayList<Prontuario> lista = new ArrayList<>();

        while (cursor.moveToNext()){
            String numero = cursor.getString(cursor.getColumnIndexOrThrow(CL_NUMERO));
            String endereco = cursor.getString(cursor.getColumnIndexOrThrow(CL_ENDERECO));
            lista.add(new Prontuario(numero, endereco));
        }
        return lista;
    }

    public boolean delete_prontuario (String numero){
        try {
            SQLiteDatabase bd = this.getWritableDatabase();
            bd.delete(TABELA_PRONTUARIO, "numero=?", new String[]{numero});
            bd.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean update_prontuario (Prontuario p) {
        try{
            SQLiteDatabase bd = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CL_ENDERECO, p.getEndereco());
            bd.update(TABELA_PRONTUARIO, values, "numero=?", new String[]{p.getNumero()});
            bd.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }




    ////////////////////// USUARIO
    public boolean inserir_usuario (Usuario u){
        try{
            SQLiteDatabase bd = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CL_NOME, u.getNome());
            cv.put(CL_SENHA, u.getSenha());
            cv.put(CL_AVATAR, u.getAvatar());
            cv.put(CL_NICKNAME, u.getNickname());

            bd.insert(TABELA_USUARIO, null, cv);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ArrayList<Usuario> listar_usuarios(){
        SQLiteDatabase bd = getReadableDatabase();
        String [] colunas = {CL_NOME, CL_SENHA, CL_NICKNAME, CL_AVATAR};
        Cursor cursor = bd.query(TABELA_USUARIO, colunas, null, null, null, null, null, null);
        ArrayList<Usuario> lista = new ArrayList<>();

        while (cursor.moveToNext()){
            String nome = cursor.getString(cursor.getColumnIndexOrThrow(CL_NOME));
            String nickname = cursor.getString(cursor.getColumnIndexOrThrow(CL_NICKNAME));
            String senha = cursor.getString(cursor.getColumnIndexOrThrow(CL_SENHA));
            String avatar = cursor.getString(cursor.getColumnIndexOrThrow(CL_AVATAR));
            lista.add(new Usuario(nome, nickname, senha, avatar));
        }
        return lista;
    }

    public boolean delete_usuario (String nickname){
        try {
            SQLiteDatabase bd = this.getWritableDatabase();
            bd.delete(TABELA_USUARIO, "nickname=?", new String[]{nickname});
            bd.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean update_usuario (Usuario u) {
        try{
            SQLiteDatabase bd = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CL_NOME, u.getNome());
            values.put(CL_SENHA, u.getSenha());
            values.put(CL_AVATAR, u.getAvatar());

            bd.update(TABELA_USUARIO, values, "nickname=?", new String[]{u.getNickname()});
            bd.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
