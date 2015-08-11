package entity;

import unalcol.instance.InstanceService;

public class QubitArrayInstance  implements InstanceService<QubitArray> {
	/**
	 * Creates a BinaryGenotype with the given lenght
	 * @param length The lengh of the new bitarray
	 */
	public QubitArrayInstance() {
	}
	
	@Override
	public Object owner() {
		// TODO Auto-generated method stub
		return QubitArray.class;
	}

	@Override
	public QubitArray get(QubitArray array) {
		// TODO Auto-generated method stub
		return new QubitArray(array.dimension(), false);
	}

}
