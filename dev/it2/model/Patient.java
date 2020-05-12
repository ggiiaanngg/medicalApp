package vn.com.med.it2.model;
import java.util.Calendar;


import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.util.Tuple;
import vn.com.med.it1.model.Department;
import vn.com.med.it1.model.Doctor;

@DClass(schema = "Med")
public abstract class Patient {
	//attribute
	
	
	//adress,phone,department?????
	public static final String A_name="name";
	public static final String A_id ="id";
	public static final String A_symptom ="symptom";

	
	@DAttr(name ="id",id=true, auto=true,type = Type.Integer,length=6,mutable=false,optional=false)
	private String id;
	private static int idCounter;
	
	@DAttr(name="name",type=Type.String,length=30,optional=false)
	  private String name;
	
	//ill
	@DAttr(name="symptom",type=Type.String,length=30,optional=false)
	  private String symptom;
	
	  // constructor method: create objects from data source
	  @DOpt(type=DOpt.Type.DataSourceConstructor)
	  protected Patient(String id, String name,  String symptom)
	      throws ConstraintViolationException {
	    this.id = nextID(id);
	    this.name = name;
	    this.symptom = symptom;
	  }
	  
	  @DOpt(type = DOpt.Type.ObjectFormConstructor)
	  @DOpt(type = DOpt.Type.RequiredConstructor)
	  protected Patient(@AttrRef("name") String name, @AttrRef("symptom") String symptom) {
		  this(null, name, symptom);
	  }
	  
	  public void setName(String name) {
		  this.name = name;
	  }
	  
	  public String getName() {
		  return name;
	  }
	  
	  public void setSymptom(String symptom) {
		  this.symptom = symptom;
	  }
	  
	  public String getSymptom() {
		  return symptom;
	  }
	  
	  @Override
	  public String toString() {
	    return toString(true);
	  }
	  
	  public String toString(boolean full) {
		    if (full)
		      return "Patient(" + id + "," + name + "," + symptom +")";
		    else
		      return "Patient(" + id + ")";
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
	    Patient other = (Patient) obj;
	    if (id == null) {
	      if (other.id != null)
	        return false;
	    } else if (!id.equals(other.id))
	      return false;
	    return true;
	  }
	  
	  // automatically generate the next patient id
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

