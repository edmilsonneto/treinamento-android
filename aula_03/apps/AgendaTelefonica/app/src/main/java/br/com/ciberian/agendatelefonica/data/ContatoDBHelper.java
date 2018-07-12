package br.com.ciberian.agendatelefonica.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import br.com.ciberian.agendatelefonica.models.Contato;

/**
 * Created by Edmilson Neto on 12/09/2017.
 */

public class ContatoDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_contato";
    private static final Integer DB_VERSION = 1;
    private static final String TABLE_NAME = "contato";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_SOBRENOME = "sobrenome";
    private static final String COLUMN_NUMERO = "numero";
    private static final String COLUMN_EMAIL = "email";



    public ContatoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOME + " TEXT," +
                    COLUMN_SOBRENOME + " TEXT," +
                    COLUMN_NUMERO + " INTEGER," +
                    COLUMN_EMAIL + " TEXT);";

            sqLiteDatabase.execSQL(query);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean save(Contato contato) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOME, contato.getNome());
        cv.put(COLUMN_SOBRENOME, contato.getSobrenome());
        cv.put(COLUMN_NUMERO, contato.getNumero());
        cv.put(COLUMN_EMAIL, contato.getEmail());

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.insert(TABLE_NAME, null, cv);

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    public boolean update(Contato contato) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOME, contato.getNome());
        cv.put(COLUMN_SOBRENOME, contato.getSobrenome());
        cv.put(COLUMN_NUMERO, contato.getNumero());
        cv.put(COLUMN_EMAIL, contato.getEmail());

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String where = COLUMN_ID + " = ?";
            String[] whereArgs = {contato.getId().toString()};

            db.update(TABLE_NAME, cv, where, whereArgs);

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    public Contato getById(Integer id) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE id = " + id;

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cs = db.rawQuery(query, null);

            Contato contato = new Contato();

            if(cs != null) {
                if(cs.moveToFirst()) {
                    contato.setId(Integer.parseInt(cs.getString(0)));
                    contato.setNome(cs.getString(1));
                    contato.setSobrenome(cs.getString(2));
                    contato.setNumero(Long.parseLong(cs.getString(3)));
                    contato.setEmail(cs.getString(4));
                }
            }

            return contato;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }

    public ArrayList<Contato> getAll() {
        ArrayList<Contato> contatos = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        try {

            Cursor cs = db.rawQuery(query, null);

            Contato contato ;

            if(cs.moveToFirst()) {
                do {
                    contato = new Contato();
                    contato.setId(Integer.parseInt(cs.getString(0)));
                    contato.setNome(cs.getString(1));
                    contato.setSobrenome(cs.getString(2));
                    contato.setNumero(Long.parseLong(cs.getString(3)));
                    contato.setEmail(cs.getString(4));

                    contatos.add(contato);
                }while (cs.moveToNext()) ;
            }

            return contatos;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }

    public boolean deleteById(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String where = COLUMN_ID + " = ?";
            String[] whereArgs = {id.toString()};

            db.delete(TABLE_NAME, where, whereArgs);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }
}
