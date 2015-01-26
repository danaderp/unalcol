/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.types.integer;
import unalcol.io.*;

import java.io.*;

/**
 *
 * @author jgomez
 */
public class IntegerReadService implements ReadService<Integer>{

    @Override
    public Object owner(){
        return Integer.class;
    }

    public static void back( char c, ShortTermMemoryReader reader ){
        if( c != (char)-1 ){
            reader.back();
        }
    }

    public static void readDigitStar( ShortTermMemoryReader reader,
                                  StringBuilder sb ) throws IOException{
        char c = (char)reader.read();
        while( Character.isDigit(c)){
            sb.append(c);
            c = (char)reader.read();
        }
        back(c, reader);
    }

    public static void removeSpaces( ShortTermMemoryReader reader ) throws IOException{
        char c = (char)reader.read();
        while( Character.isSpaceChar(c)){
            c = (char)reader.read();
        }
        back(c, reader);
    }

    @Override
    public Integer read(Integer i, ShortTermMemoryReader reader) throws
            RowColumnReaderException{
        return read(reader);
    }

    public Integer read(ShortTermMemoryReader reader) throws
            RowColumnReaderException{
        try{
            removeSpaces(reader);
            char c = (char)reader.read();
            if( Character.isDigit(c) || c=='-' || c=='+' ){
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                readDigitStar(reader, sb);
                return Integer.parseInt(sb.toString());
            }
            throw new Exception("Unexpected symbol " + c);
        }catch( Exception e ){
            throw reader.getException("Integer Parser Error "+e.getMessage());
        }

    }

    public static void main(String[] args ){
        StringReader r = new StringReader("    --1234,");
        ShortTermMemoryReader reader = new ShortTermMemoryReader(r);
        IntegerReadService service = new IntegerReadService();
        try{
           Integer i = service.read(reader);
           System.out.println(i);
        }catch(Exception e ){
            System.out.println( ((RowColumnReaderException)e).getColumn() );
            e.printStackTrace();
        }
    }
}
