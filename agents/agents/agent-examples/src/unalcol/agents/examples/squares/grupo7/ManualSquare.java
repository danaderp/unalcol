package unalcol.agents.examples.squares.grupo7;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;

public class ManualSquare implements AgentProgram {

	public ManualSquare(String wHITE) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action compute(Percept p) {
		System.out.println("Your turn:");
		Scanner in = new Scanner(new InputStreamReader(System.in));
		int i = in.nextInt();
		int j = in.nextInt();
		String side = in.next();
		System.out.println("ok");
		return new Action(i+":"+j+":"+side);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
