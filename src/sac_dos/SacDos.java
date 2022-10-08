package sac_dos;

import java.rmi.activation.UnknownObjectException;

import ilog.concert.IloException;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class SacDos {
	private double[] p;
	private double[] u;
	private double cap;
	private int n;
	IloCplex modele;
	IloNumVar[] x;

	public SacDos(double[] p, double[] u, double cap) {
		this.p = p;
		this.u = u;
		this.cap = cap;
		n = p.length;

		try {
			modele = new IloCplex();
			creatModele();
			System.out.println(modele.toString());
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void creatModele() {
		// TODO Auto-generated method stub
		creaVariable();
		creaContrainte();
		creaObjective();
	}

	private void creaObjective() {
		// TODO Auto-generated method stub
		try {
			IloNumExpr f = modele.scalProd(x, u);
			modele.addMaximize(f);
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void creaContrainte() {
		// TODO Auto-generated method stub
		try {
			IloNumExpr c1 = modele.scalProd(x, p);
			modele.addLe(c1, cap);
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean solve() {
		boolean resol = false;
		try {
			resol = modele.solve();
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resol;
	}

	private void creaVariable() {
		// TODO Auto-generated method stub
		try {
			x = modele.boolVarArray(n);
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double[] getSolution() throws UnknownObjectException {
		double[] a = null;
		if (solve())
			try {
				a = modele.getValues(x);
			} catch (IloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return a;
	}

	public static void main(String[] args) throws UnknownObjectException {
		// TODO Auto-generated method stub
		double[] p = { 12, 2, 1, 4, 1 };
		double[] u = { 4, 2, 1, 10, 2 };
		SacDos s1 = new SacDos(p, u, 15);
		double[] d = s1.getSolution();
		for (int i = 0; i < d.length; i++) {
			System.out.print("\t" + d[i]);
		}

	}

}
