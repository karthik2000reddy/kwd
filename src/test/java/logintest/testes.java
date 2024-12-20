package logintest;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.testng.annotations.Test;

import basepage.baseclass;
import keyword.engine.keyword_engine;

public class testes  extends baseclass{

    public keyword_engine keyword_engine;

    @Test
    public void logintestcase() throws InvalidFormatException {
        keyword_engine = new keyword_engine();
        keyword_engine.startExecution("login"); 
    }
}
