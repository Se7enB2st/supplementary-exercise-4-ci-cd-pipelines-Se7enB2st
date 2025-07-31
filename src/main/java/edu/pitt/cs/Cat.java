package edu.pitt.cs;

import org.mockito.Mockito;
import static org.mockito.Mockito.*; 

public interface Cat {
	public static Cat createInstance(InstanceType type, int id, String name) {
		switch (type) {
			case IMPL:
				return new CatImpl(id, name);
			case BUGGY:
				return new CatBuggy(id, name);
			case SOLUTION:
				return new CatSolution(id, name);
			case MOCK:
			    Cat mockCat = mock(Cat.class);
			    final int[] rented = {0}; // 0 = not rented, 1 = rented
			    final String[] catName = {name};
			    when(mockCat.getId()).thenReturn(id);
			    when(mockCat.getName()).thenAnswer(invocation -> catName[0]);
			    when(mockCat.toString()).thenAnswer(invocation -> "ID " + id + ". " + catName[0]);
			    when(mockCat.getRented()).thenAnswer(invocation -> rented[0] == 1);
			    doAnswer(invocation -> { rented[0] = 1; return null; }).when(mockCat).rentCat();
			    doAnswer(invocation -> { rented[0] = 0; return null; }).when(mockCat).returnCat();
			    doAnswer(invocation -> { catName[0] = invocation.getArgument(0); return null; }).when(mockCat).renameCat(anyString());
			    return mockCat;
			default:
				assert(false);
				return null;
		}
	}

	// WARNING: You are not allowed to change any part of the interface.
	// That means you cannot add any method nor modify any of these methods.
	
	public void rentCat();

	public void returnCat();

	public void renameCat(String name);

	public String getName();

	public int getId();

	public boolean getRented();

	public String toString();
}
