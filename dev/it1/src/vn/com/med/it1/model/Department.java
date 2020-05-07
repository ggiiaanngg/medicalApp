package vn.com.med.it1.model;
import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.util.Tuple;


@DClass(schema="Med")
public class Department {
	
	@DAttr(name="id" ,id=true ,auto=true,length=3,mutable=false, optional=false,type=Type.Integer)
	private int id;
	private static int idCounter;
	
	@DAttr(name="name",type=Type.String,length=20,optional=false)
	private String name;
	
	@DAttr(name="doctor",type=Type.Domain,serialisable = false)
	@DAssoc(ascName = "doctor-has_dept",role="dept", ascType = AssocType.One2One,endType = AssocEndType.One, associate=@Associate(type=Doctor.class,cardMin=1,cardMax=1,determinant=true))
	private Doctor doctor;
	
	//form object form:doctor is not included
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	@DOpt(type=DOpt.Type.RequiredConstructor)
	public Department(@AttrRef("name") String deptName) {
		this(null, deptName , null);
	}
	
	// from object form: doctor is included
	  @DOpt(type=DOpt.Type.ObjectFormConstructor)
	  public Department(@AttrRef("name") String deptName, @AttrRef("doctor") Doctor doctor) {
	    this(null, deptName, doctor);
	  }
	  
	  //from data source
	  @DOpt(type = DOpt.Type.DataSourceConstructor)
	  public Department(@AttrRef("id") Integer id, @AttrRef("name") String deptName) {
		  this(id, deptName, null);
	  }

	  //based constructor
		public Department(Integer id, String deptName, Doctor doctor) {
			// TODO Auto-generated constructor stub
			this.id=nextId(id);
			this.name= deptName;
			this.doctor=doctor;
		}
		
		
		private static int nextId(Integer currID) {
			if(currID ==null) {
				idCounter++;
				return idCounter;
			} else {
				int num = currID.intValue();
				if (num > idCounter) 
					idCounter = num;
					
					return currID;	
				}			
			}
		 /**
		   * @requires 
		   *  minVal != null /\ maxVal != null
		   * @effects 
		   *  update the auto-generated value of attribute <tt>attrib</tt>, specified for <tt>derivingValue</tt>, using <tt>minVal, maxVal</tt>
		   */
		@DOpt(type = DOpt.Type.AutoAttributeValueSynchroniser)
		public static void updateAutoGenaratedValue(
				DAttr attrib,
				Tuple derivingValue,
				Object minVal,
				Object maxVal) throws ConstraintViolationException {
			
			if (minVal !=  null && maxVal != null) {
				//TODO: update this for the correct attribute if there are more than one auto attributes of this class 
				int maxIdVal = (Integer) maxVal;
				if (maxIdVal > idCounter)
					idCounter = maxIdVal;
				
			}
				
				
		}

		public int getId() {
			return id;
		}


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Doctor getDoctor() {
		    return doctor;
		}  
		@DOpt(type=DOpt.Type.LinkAdderNew)
		public void setNewDoctor(Doctor doctor) {
			this.doctor=doctor;
			//do other updates here (if needed)
		}

		public void setDoctor(Doctor doctor) {
			this.doctor = doctor;
		}
		
		 @Override
		  public String toString() {
		    return name;
		  }
	
	
	
}
