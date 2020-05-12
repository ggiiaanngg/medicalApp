package vn.com.med.it1.model;

import java.util.Calendar;
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
public class Doctor {
	public static final String A_name="name";
	public static final String A_id ="id";
	public static final String A_department="dept";
	public static final String A_phone="phone";
	public static final String A_spec="spec";
	public static final String A_optional="optSpec";
	public static final String A_email="email";
	
	
	//attribute of doctor;
	
	@DAttr(name = A_id, id = true, type = Type.String, auto = true, length=6, mutable = false, optional=false)
	private String id;
	//keep track of doctor by id 
	private static int idCounter =0;
	
	@DAttr(name= A_name, type = Type.String, length = 30, optional = false )
	private String name;
	
	@DAttr(name = A_spec,type = Type.String, optional = false)
	private String spec;
	
	@DAttr(name = A_optional,type = Type.String, optional =false)
	private String optSpec;
	
	@DAttr(name=A_department, type=Type.String, length = 20, optional = true)
	@DAssoc(ascName="doctor-has-dept",role="doctor", ascType = AssocType.One2One, endType = AssocEndType.One, associate = @Associate(type=Department.class,cardMin=1,cardMax = 1))
	private Department dept;
	@DAttr(name = A_email, type = Type.String, length= 30, optional =false)
	private String email;
	
	@DAttr(name = A_phone, type = Type.String, length= 10, optional =false)
	private String phone;
	
	//constructor methods for creating in the application without SClass
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public Doctor (@AttrRef("name") String name, @AttrRef("dept") Department dept, @AttrRef("phone") String phone, @AttrRef("spec") String spec, @AttrRef("optSpec") String optSpec, @AttrRef("email") String email) {
		this(null, name,dept, phone, spec, optSpec , email);
	}
	//shared constructor that invoked by other constructor
	
	 @DOpt(type=DOpt.Type.DataSourceConstructor)
	  public Doctor(String id, String name, Department dept, String phone, String spec, String email, String optSpec) 
	  throws ConstraintViolationException {
	    // generate an id
	    this.id = nextID(id);

	    // assign other values
	    this.name = name;
	    this.dept = dept;
	    this.phone = phone;
	    this.spec = spec;
	    this.optSpec = optSpec;
	    this.email = email;
	  }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	public String getOptSpec() {
		return optSpec;
	}
	public void setOptSpec(String optSpec) {
		this.optSpec = optSpec ;
	}
	
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	  // override toString
	  /**
	   * @effects returns <code>this.id</code>
	   */
	  @Override
	  public String toString() {
	    return toString(true);
	  }


	  /**
	   * @effects returns <code>Doctor(id,name,dept,phone,email)</code>.
	   */
	  public String toString(boolean full) {
	    if (full)
	      return "Doctor(" + id + "," + name + "," + phone + "," + dept + "," + spec + ","
	          + email + ")";
	    else
	      return "Doctor(" + id + ")";
	  }
	  
	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    return result;
	  }
	  
	  
	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    Doctor other = (Doctor) obj;
	    if (id == null) {
	      if (other.id != null)
	        return false;
	    } else if (!id.equals(other.id))
	      return false;
	    return true;
	  }
	  
	  // automatically generate the next doctor id
	  private String nextID(String id) throws ConstraintViolationException {
	    if (id == null) { // generate a new id
	      if (idCounter == 0) {
	        idCounter = Calendar.getInstance().get(Calendar.YEAR);
	      } else {
	        idCounter++;
	      }
	      return "S" + idCounter;
	    } else {
	      // update id
	      int num;
	      try {
	        num = Integer.parseInt(id.substring(1));
	      } catch (RuntimeException e) {
	        throw new ConstraintViolationException(
	            ConstraintViolationException.Code.INVALID_VALUE, e, new Object[] { id });
	      }
	      
	      if (num > idCounter) {
	        idCounter = num;
	      }
	      
	      return id;
	    }
	  }
	  
	  
	  @DOpt(type=DOpt.Type.AutoAttributeValueSynchroniser)
	  public static void updateAutoGeneratedValue(
	      DAttr attrib,
	      Tuple derivingValue, 
	      Object minVal, 
	      Object maxVal) throws ConstraintViolationException {
	    
	    if (minVal != null && maxVal != null) {
	      //TODO: update this for the correct attribute if there are more than one auto attributes of this class 

	      String maxId = (String) maxVal;
	      
	      try {
	        int maxIdNum = Integer.parseInt(maxId.substring(1));
	        
	        if (maxIdNum > idCounter) // extra check
	          idCounter = maxIdNum;
	        
	      } catch (RuntimeException e) {
	        throw new ConstraintViolationException(
	            ConstraintViolationException.Code.INVALID_VALUE, e, new Object[] {maxId});
	      }
	    }
	  }

	  
}

