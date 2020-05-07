package vn.com.med.it1.software;

import domainapp.basics.exceptions.NotPossibleException;
import domainapp.basics.software.DomainAppToolSoftware;
import vn.com.med.it1.model.Department;
import vn.com.med.it1.model.Doctor;

public class MedSoftware extends DomainAppToolSoftware {

	//the domain model of software
	@SuppressWarnings("rawtypes")
	private static final Class[] model = {
			Doctor.class,
			Department.class,
	};

	@SuppressWarnings("rawtypes")
	@Override
	protected Class[] getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	public static void main (String[] args) throws NotPossibleException {
		new MedSoftware().exec(args);
	}
}
