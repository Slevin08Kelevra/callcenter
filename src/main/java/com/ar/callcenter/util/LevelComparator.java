package com.ar.callcenter.util;

import java.util.Comparator;

import com.ar.callcenter.entities.Employee;



/** Clase finalmente no usada uso comparable para el blocking queue.
 * 
 * @author pablo.paparini */
public class LevelComparator implements Comparator<Employee> {

  /* (non-Javadoc)
   * 
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object) */
  public int compare(Employee e1, Employee e2) {

    if (e1.getPriority() < e2.getPriority()) {
      return -1;
    } else if (e1.getPriority() > e2.getPriority()) {
      return 1;
    } else {
      return 0;
    }

  }

}
