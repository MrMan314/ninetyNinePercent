class ByteArray {
	public static byte[] merge(byte[] array1, byte[] array2) {
		byte[] mergedArray=new byte[array1.length+array2.length];
		for(int i=0; i<array1.length; i++) {
			mergedArray[i]=array1[i];
		}
		for(int i=array1.length; i<mergedArray.length; i++) {
			mergedArray[i]=array2[i-array1.length];
		}
		return mergedArray;
	}
}