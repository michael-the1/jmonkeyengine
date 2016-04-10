package com.jme3.math;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class Matrix4fTest {

	public Matrix4f m;
	public Matrix4f invertible_matrix;
	public Matrix4f m1_16;

	public Vector3f vec3f;
	public Vector4f vec4f;

	@Before
	public void setUp() {
		m = new Matrix4f();

		m1_16 = new Matrix4f(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);

		invertible_matrix = new Matrix4f(
				3, 0, 2, -1,
				1, 2, 0, -2,
				1, 0, 2, -3,
				3, 0, 2, 0
		);

		vec3f = new Vector3f(1, 1, 1);
		vec4f = new Vector4f(1, 1, 1, 1);
	}

	/**
	 * Assert whether the Matrix4f object contains the values we expect.
	 * Since we're comparing floats, we use a margin of error of 1e-8f.
	 *
	 * Example usage:
	 *
	 * Matrix4f matrix_to_test = new Matrix4f();
	 *
	 * float[] expected = {
	 *     1, 0, 0, 0,
	 *     0, 1, 0, 0,
	 *     0, 0, 1, 0,
	 *     0, 0, 0, 1
	 * };
	 *
	 * assertMatrixEquals(expected, matrix_to_test);
	 *
	 * @param expected A float array of expected values in RowMajor ordering.
	 * @param actual The Matrix4f object to test.
	 */
	public void assertMatrixEquals(float[] expected, Matrix4f actual) {
		assertMatrixEquals(expected, actual, 1e-4f);
	}

	/**
	 * Assert whether the Matrix4f object contains the values we expect.
	 * @param expected An array of arrays of floats. Each nested array represents a row.
	 * @param actual The Matrix4f object to test.
	 * @param epsilon The margin of error we accept when comparing floats.
	 */
	public void assertMatrixEquals(float[] expected, Matrix4f actual, float epsilon) {
		assertEquals(expected[0], actual.m00, epsilon);
		assertEquals(expected[1], actual.m01, epsilon);
		assertEquals(expected[2], actual.m02, epsilon);
		assertEquals(expected[3], actual.m03, epsilon);

		assertEquals(expected[4], actual.m10, epsilon);
		assertEquals(expected[5], actual.m11, epsilon);
		assertEquals(expected[6], actual.m12, epsilon);
		assertEquals(expected[7], actual.m13, epsilon);

		assertEquals(expected[8], actual.m20, epsilon);
		assertEquals(expected[9], actual.m21, epsilon);
		assertEquals(expected[10], actual.m22, epsilon);
		assertEquals(expected[11], actual.m23, epsilon);

		assertEquals(expected[12], actual.m30, epsilon);
		assertEquals(expected[13], actual.m31, epsilon);
		assertEquals(expected[14], actual.m32, epsilon);
		assertEquals(expected[15], actual.m33, epsilon);
	}

	public void assertVectorEquals(float[] expected, Vector3f actual) {
		assertVectorEquals(expected, actual, 1e-8f);
	}

	public void assertVectorEquals(float[] expected, Vector3f actual, float epsilon) {
		assertEquals(3, expected.length);
		assertEquals(expected[0], actual.x, epsilon);
		assertEquals(expected[1], actual.y, epsilon);
		assertEquals(expected[2], actual.z, epsilon);
	}

	public void assertVectorEquals(float[] expected, Vector4f actual) {
		assertVectorEquals(expected, actual, 1e-8f);
	}

	public void assertVectorEquals(float[] expected, Vector4f actual, float epsilon) {
		assertEquals(4, expected.length);
		assertEquals(expected[0], actual.x, epsilon);
		assertEquals(expected[1], actual.y, epsilon);
		assertEquals(expected[2], actual.z, epsilon);
		assertEquals(expected[3], actual.w, epsilon);
	}

	@Test
	public void testConstructorNoParameters() {

		float[] expected = {
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1,
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testConstructorWithParameters() {
		m = new Matrix4f(1.0f, 1.0f, 1.0f, 1.0f,
						 1.0f, 1.0f, 1.0f, 1.0f,
						 1.0f, 1.0f, 1.0f, 1.0f,
						 1.0f, 1.0f, 1.0f, 1.0f
		);

		float[] expected = {
			1, 1, 1, 1,
			1, 1, 1, 1,
			1, 1, 1, 1,
			1, 1, 1, 1
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testConstructorWithArray() {
		float[] input = {
			1, 2, 3, 4,
			1, 2, 3, 4,
			1, 2, 3, 4,
			1, 2, 3, 4
		};

		m = new Matrix4f(input);

		float[] expected = {
			1, 1, 1, 1,
			2, 2, 2, 2,
			3, 3, 3, 3,
			4, 4, 4, 4
		};

		assertMatrixEquals(expected, m);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithEmptyArray() {
		new Matrix4f(new float[] {});
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithArrayWrongSize() {
		new Matrix4f(new float[] {1, 2, 3, 4});
	}

	@Test(expected=NullPointerException.class)
	public void testConstructorWithNullArray() {
		float[] input = null;
		new Matrix4f(input);
	}

	@Test
	public void testConstructorWithMatrixInput() {
		Matrix4f input = new Matrix4f(1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3);
		m = new Matrix4f(input);

		float[] expected = {
				1, 3, 1, 3,
				1, 3, 1, 3,
				1, 3, 1, 3,
				1, 3, 1, 3
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testIsIdentity() {
		assertTrue(m.isIdentity());
	}

	@Test
	public void testIsNotIdentity() {
		m = new Matrix4f(
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			1, 0, 0, 1
		);

		assertFalse(m.isIdentity());
	}

	@Test
	public void testIsIdentityFloatTesting() {
		m = new Matrix4f(
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1.0000001f
		);

		assertFalse(m.isIdentity());
	}

	@Test
	public void testIsIdentityFloatTestingAllowed() {
		// Float comparison is allowed to be accurate up to 1e-8.
		m = new Matrix4f(
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1.00000001f
		);

		assertTrue(m.isIdentity());
	}

	@Test
	public void testEqualIdentity() {
		assertTrue(Matrix4f.equalIdentity(m));
	}

	@Test
	public void testNotEqualIdentify() {
		assertFalse(Matrix4f.equalIdentity(m1_16));
	}

	@Test(expected=NullPointerException.class)
	public void testEqualIdentityNullInput() {
		Matrix4f.equalIdentity(null);
	}

	@Test
	public void testEqualIdentityFloatTestingAllowed() {
		// Float comparison is allowed to be accurate up to 1e-8.
		m = new Matrix4f(
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1.00000001f
		);

		assertTrue(Matrix4f.equalIdentity(m));
	}


	@Test
	public void testZeroMatrix() {
		float[] expected = {
				0, 0, 0, 0,
				0, 0, 0, 0,
				0, 0, 0, 0,
				0, 0, 0, 0
		};

		assertMatrixEquals(expected, Matrix4f.ZERO);
	}

	@Test
	public void testIdentityMatrix() {
		assertTrue(Matrix4f.IDENTITY.isIdentity());
	}

	@Test
	public void testCopy() {
		Matrix4f m1 = new Matrix4f(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2);
		Matrix4f m2 = new Matrix4f();

		m1.copy(m2);

		assertEquals(m1, m2);
		assertTrue(m1.isIdentity());
	}

	@Test
	public void testCopyNull() {
		m = new Matrix4f(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

		m.copy(null);

		assertEquals(m, Matrix4f.IDENTITY);
	}

	@Test
	public void testFromFrame() {
		Vector3f location = new Vector3f(vec3f);
		Vector3f direction = new Vector3f(vec3f);
		Vector3f up = new Vector3f(vec3f);
		Vector3f left = new Vector3f(vec3f);

		m.fromFrame(location, direction, up, left);

		float[] expected = {
				0, 0, 0, 0,
				0, 0, 0, 0,
				-1, -1, -1, 3,
				0, 0, 0, 1
		};

		assertMatrixEquals(expected, m);
	}

	@Test(expected=NullPointerException.class)
	public void testGetNullMatrix() {
		m.get(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetMatrixWrongSize() {
		float[] matrix = new float[15];
		m.get(matrix);
	}

	@Test
	public void testGetMatrix() {
		float[] matrix = new float[16];
		m1_16.get(matrix);

		float[] expected = {
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 15, 16
		};

		assertArrayEquals(expected, matrix, 1e-8f);
	}

	@Test
	public void testGetMatrixColumnMajorOrder() {
		float[] matrix = new float[16];
		m1_16.get(matrix, false);

		float[] expected = {
			1, 5, 9, 13,
			2, 6, 10, 14,
			3, 7, 11, 15,
			4, 8, 12, 16
		};

		assertArrayEquals(expected, matrix, 1e-8f);
	}

	@Test
	public void testGetIndex(){
		float result = m.get(0, 0);
		assertEquals(1.0f, result, 1e-8);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetIndexRowOutOfBounds(){
		m.get(-1, 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetIndexColumnOutOfBounds(){
		m.get(0, 5);
	}

	@Test
	public void testGetFirstColumn() {
		float[] column = m.getColumn(0);
		assertArrayEquals(new float[] {1, 0, 0, 0}, column, 1e-8f);
	}

	@Test
	public void testGetSecondColumn() {
		float[] column = m.getColumn(1);
		assertArrayEquals(new float[] {0, 1, 0, 0}, column, 1e-8f);
	}

	@Test
	public void testGetThirdColumn() {
		float[] column = m.getColumn(2);
		assertArrayEquals(new float[] {0, 0, 1, 0}, column, 1e-8f);
	}

	@Test
	public void testGetFourthColumn() {
		float[] column = m.getColumn(3);
		assertArrayEquals(new float[] {0, 0, 0, 1}, column, 1e-8f);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetColumnOutOfBounds() {
		m.getColumn(5);
	}

	@Test
	public void testGetColumnWithStore() {
		float[] store = new float[4];
		m.getColumn(0, store);
		assertArrayEquals(new float[] {1, 0, 0, 0}, store, 1e-8f);
	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testGetColumnWithSmallStore() {
		float[] store = new float[3];
		m.getColumn(0, store);
	}

	@Test
	public void testSetFirstColumn() {
		m.setColumn(0, new float[] {4, 4, 4, 4});
		assertArrayEquals(new float[] {4, 4, 4, 4}, m.getColumn(0), 1e-8f);
	}

	@Test
	public void testSetSecondColumn() {
		m.setColumn(1, new float[] {4, 4, 4, 4});
		assertArrayEquals(new float[] {4, 4, 4, 4}, m.getColumn(1), 1e-8f);
	}

	@Test
	public void testSetThirdColumn() {
		m.setColumn(2, new float[] {4, 4, 4, 4});
		assertArrayEquals(new float[] {4, 4, 4, 4}, m.getColumn(2), 1e-8f);
	}

	@Test
	public void testSetFourthColumn() {
		m.setColumn(3, new float[] {4, 4, 4, 4});
		assertArrayEquals(new float[] {4, 4, 4, 4}, m.getColumn(3), 1e-8f);
	}

	@Test(expected=NullPointerException.class)
	public void testSetColumnNullInput() {
		m.setColumn(0, null);
	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testSetColumnSmallInput() {
		m.setColumn(0, new float[] {1, 1, 1});
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetColumnInvalidIndex() {
		m.setColumn(5, new float[] {1, 1, 1, 1});
	}

	@Test
	public void testSetIndex() {
		m.set(0, 0, 4);
		assertEquals(4.0f, m.m00, 1e-8f);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetIndexOutOfBoundsRow() {
		m.set(-1, 0, 4);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetIndexOutOfBoundsColumn() {
		m.set(0, 5, 4);
	}

	@Test
	public void testSetWithNestedArray() {
		float[][] input = {
			{1, 2, 3, 4},
			{1, 2, 3, 4},
			{1, 2, 3, 4},
			{1, 2, 3, 4}
		};

		m.set(input);

		float[] expected = {
			1, 2, 3, 4,
			1, 2, 3, 4,
			1, 2, 3, 4,
			1, 2, 3, 4
		};

		assertMatrixEquals(expected, m);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetWithSmallerOuterArray() {
		float[][] input = new float[][] {
			{1, 2, 3, 4},
			{1, 2, 3, 4},
			{1, 2, 3, 4}
		};

		m.set(input);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetWithSmallerFirstInnerArray() {
		float[][] input = new float[][] {
			{1},
			{1, 2, 3, 4},
			{1, 2, 3, 4},
			{1, 2, 3, 4}
		};

		m.set(input);
	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testSetWithSmallerNonFirstInnerArray() {
		float[][] input = new float[][] {
			{1, 2, 3, 4},
			{1, 2, 3, 4},
			{1},
			{1, 2, 3, 4}
		};

		m.set(input);
	}

	@Test
	public void testSetWithParameters() {
		m.set(1.0f, 1.0f, 1.0f, 1.0f,
				 1.0f, 1.0f, 1.0f, 1.0f,
				 1.0f, 1.0f, 1.0f, 1.0f,
				 1.0f, 1.0f, 1.0f, 1.0f
		);

		float[] expected = {
			1, 1, 1, 1,
			1, 1, 1, 1,
			1, 1, 1, 1,
			1, 1, 1, 1
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testSetWithMatrixArgument() {
		m.set(Matrix4f.ZERO);

		float[] expected = {
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testSetWithArrayRowMajorOrder() {
		float[] input = {
			1, 2, 3, 4,
			5, 6, 7, 8,
			9, 10, 11, 12,
			13, 14, 15, 16
		};

		m.set(input);

		float[] expected = {
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 15, 16
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testTranspose() {
		m = new Matrix4f(
				1, 2, 3, 4,
				1, 2, 3, 4,
				1, 2, 3, 4,
				1, 2, 3, 4
		);

		Matrix4f transposed_matrix = m.transpose();

		float[] expected = {
				1, 1, 1, 1,
				2, 2, 2, 2,
				3, 3, 3, 3,
				4, 4, 4, 4
		};

		assertMatrixEquals(expected, transposed_matrix);
	}

	@Test
	public void testTransposeLocal() {
		m = new Matrix4f(
				1, 2, 3, 4,
				1, 2, 3, 4,
				1, 2, 3, 4,
				1, 2, 3, 4
		);

		m.transposeLocal();

		float[] expected = {
				1, 1, 1, 1,
				2, 2, 2, 2,
				3, 3, 3, 3,
				4, 4, 4, 4
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testFillFloatArray() {
		float[] store = new float[16];

		m1_16.fillFloatArray(store, false);

		float[] expected = {
			1, 2, 3, 4,
			5, 6, 7, 8,
			9, 10, 11, 12,
			13, 14, 15, 16
		};

		assertArrayEquals(expected, store, 1e-8f);
	}

	@Test
	public void testFillFloatArrayColumnMajor() {
		float[] store = new float[16];

		m1_16.fillFloatArray(store, true);

		float[] expected = {
			1, 5, 9, 13,
			2, 6, 10, 14,
			3, 7, 11, 15,
			4, 8, 12, 16
		};

		assertArrayEquals(expected, store, 1e-8f);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFillFloatArraySmallArray() {
		float[] store = new float[1];
		m1_16.fillFloatArray(store, false);
	}

	@Test(expected=NullPointerException.class)
	public void testFillFloatArrayNullInput() {
		float[] store = null;
		m1_16.fillFloatArray(store, false);
	}

	@Test
	public void testLoadIdentity() {
		m.loadIdentity();
		assertTrue(m.isIdentity());
	}

	@Test
	public void testFromAngleAxis() {
		float angle = FastMath.PI / 2;
		m.fromAngleAxis(angle, vec3f);

		float u = 1 / (float) Math.sqrt(3f);

		float[] expected = {
			1/3f, 1/3f - u, 1/3f + u, 0,
			1/3f + u, 1/3f, 1/3f - u, 0,
			1/3f - u, 1/3f + u, 1/3f, 0,
			0, 0, 0, 1
		};

		assertMatrixEquals(expected, m, 1e-4f);
	}
	
	@Test
	public void testFromAngleNormalAxisWithNormalVectorInput() {
		m.fromAngleNormalAxis(0f, vec3f.normalize());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFromAngleNormalAxisWithNotNormalVectorInput() {
		m.fromAngleNormalAxis(0f, vec3f);
	}

	@Test
	public void testMultLocal() {
		m.multLocal(2.0f);

		float[] expected = {
			2, 0, 0, 0,
			0, 2, 0, 0,
			0, 0, 2, 0,
			0, 0, 0, 2
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testMult() {
		Matrix4f multiplied_matrix = m.mult(2.0f);

		float[] expected = {
				2, 0, 0, 0,
				0, 2, 0, 0,
				0, 0, 2, 0,
				0, 0, 0, 2
		};

		assertMatrixEquals(expected, multiplied_matrix);
		assertTrue(m.isIdentity());
	}

	@Test
	public void testMultWithStore() {
		Matrix4f store = new Matrix4f();
		m.mult(2.0f, store);

		float[] expected = {
				2, 0, 0, 0,
				0, 2, 0, 0,
				0, 0, 2, 0,
				0, 0, 0, 2
		};

		assertMatrixEquals(expected, store);
		assertTrue(m.isIdentity());
	}

	@Test
	public void testMultMatrix() {
		Matrix4f m1 = new Matrix4f(m1_16);
		Matrix4f m2 = new Matrix4f(m1_16);
		Matrix4f multiplied_matrix = m1.mult(m2);

		float[] expected = {
			90, 100, 110, 120,
			202, 228, 254, 280,
			314, 356, 398, 440,
			426, 484, 542, 600
		};

		assertMatrixEquals(expected, multiplied_matrix);
	}

	@Test
	public void testMultMatrixWithItself() {
		Matrix4f multiplied_matrix = m1_16.mult(m1_16);

		float[] expected = {
			90, 100, 110, 120,
			202, 228, 254, 280,
			314, 356, 398, 440,
			426, 484, 542, 600
		};

		float[] original = {
				1, 2, 3, 4,
				5, 6, 7, 8,
				9, 10, 11, 12,
				13, 14, 15, 16
		};

		assertMatrixEquals(expected, multiplied_matrix);
		assertMatrixEquals(original, m1_16);
	}

	@Test
	public void testMultMatrixWithStore() {
		Matrix4f m1 = new Matrix4f(m1_16);
		Matrix4f m2 = new Matrix4f(m1_16);
		Matrix4f store = new Matrix4f();
		Matrix4f multiplied_matrix = m1.mult(m2, store);

		float[] expected = {
			90, 100, 110, 120,
			202, 228, 254, 280,
			314, 356, 398, 440,
			426, 484, 542, 600
		};

		assertMatrixEquals(expected, multiplied_matrix);
		assertMatrixEquals(expected, store);
	}

	@Test
	public void testMultMatrixWhereInputIsAlsoStore() {
		Matrix4f m1 = new Matrix4f(m1_16);
		Matrix4f m2 = new Matrix4f(m1_16);
		Matrix4f multiplied_matrix = m1.mult(m2, m2);

		float[] expected = {
			90, 100, 110, 120,
			202, 228, 254, 280,
			314, 356, 398, 440,
			426, 484, 542, 600
		};

		assertMatrixEquals(expected, multiplied_matrix);
		assertMatrixEquals(expected, m2);
	}

	@Test
	public void testMultMatrixWithItselfAsStore() {
		Matrix4f multiplied_matrix = m1_16.mult(m1_16, m1_16);

		float[] expected = {
			90, 100, 110, 120,
			202, 228, 254, 280,
			314, 356, 398, 440,
			426, 484, 542, 600
		};

		assertMatrixEquals(expected, multiplied_matrix);
		assertMatrixEquals(expected, m1_16);
	}

	@Test
	public void testMultMatrixLocal() {
		Matrix4f local_matrix = new Matrix4f(m1_16);
		Matrix4f multiplied_matrix = local_matrix.multLocal(m1_16);

		float[] expected = {
				90, 100, 110, 120,
				202, 228, 254, 280,
				314, 356, 398, 440,
				426, 484, 542, 600
		};

		assertMatrixEquals(expected, multiplied_matrix);
		assertMatrixEquals(expected, local_matrix);
	}

	@Test
	public void testMultArray() {
		float[] vec = {1, 1, 1, 1};

		m1_16.mult(vec);

		float[] expected = {10, 26, 42, 58};
		assertArrayEquals(expected, vec, 1e-8f);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMultSmallerArray() {
		float[] vec = {1, 1, 1};

		m1_16.mult(vec);
	}

	@Test(expected=NullPointerException.class)
	public void testMultNullArray() {
		float[] null_vec = null;

		m1_16.mult(null_vec);
	}

	@Test
	public void testMultAcross() {
		float[] vec = {1, 1, 1, 1};

		m1_16.multAcross(vec);

		float[] expected = {28, 32, 36, 40};
		assertArrayEquals(expected, vec, 1e-8f);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMultAcrossSmallerArray() {
		float[] vec = {1, 1, 1};

		m1_16.multAcross(vec);
	}

	@Test(expected=NullPointerException.class)
	public void testMultAcrossNullArray() {
		float[] null_vec = null;

		m1_16.multAcross(null_vec);
	}

	@Test
	public void testMultVector3f() {
		Vector3f rotated_vec = m1_16.mult(vec3f);

		float[] expected = {10, 26, 42};

		assertVectorEquals(expected, rotated_vec);
	}

	@Test
	public void testMultVector3fWithStore() {
		Vector3f store = new Vector3f();
		Vector3f rotated_vec = m1_16.mult(vec3f, store);

		float[] expected = {10, 26, 42};

		assertVectorEquals(expected, rotated_vec);
		assertVectorEquals(expected, store);
		assertSame(rotated_vec, store);
	}

	@Test
	public void testMultVector3fWithItselfAsStore() {
		Vector3f rotated_vec = m1_16.mult(vec3f, vec3f);

		float[] expected = {10, 26, 42};

		assertVectorEquals(expected, rotated_vec);
		assertVectorEquals(expected, vec3f);
		assertSame(rotated_vec, vec3f);
	}

	@Test
	public void testMultVector3fAcross() {
		Vector3f store = new Vector3f();
		Vector3f rotated_normal_vec = m1_16.multAcross(vec3f, store);

		float[] expected = {28, 32, 36};

		assertVectorEquals(expected, store);
		assertVectorEquals(expected, rotated_normal_vec);
		assertSame(rotated_normal_vec, store);
	}

	@Test(expected=NullPointerException.class)
	public void testMultVector3fNullInput() {
		Vector3f store = new Vector3f();
		m1_16.multAcross(null, store);
	}

	@Test
	public void testMultVector3fAcrossNullStore() {
		Vector3f rotated_normal_vec = m1_16.multAcross(vec3f, null);

		float[] expected = {28, 32, 36};

		assertVectorEquals(expected, rotated_normal_vec);
	}

	@Test
	public void testMultVector3fAcrossWithItselfAsStore() {
		Vector3f rotated_normal_vec = m1_16.multAcross(vec3f, vec3f);

		float[] expected = {28, 32, 36};

		assertVectorEquals(expected, vec3f);
		assertVectorEquals(expected, rotated_normal_vec);
		assertSame(rotated_normal_vec, vec3f);
	}

	@Test
	public void testMultVector3fNormal() {
		Vector3f store = new Vector3f();
		Vector3f rotated_normal_vec = m1_16.multNormal(vec3f, store);

		float[] expected = {6, 18, 30};

		assertVectorEquals(expected, store);
		assertVectorEquals(expected, rotated_normal_vec);
		assertSame(rotated_normal_vec, store);
	}

	@Test
	public void testMultVector3fNormalNullStore() {
		Vector3f rotated_normal_vec = m1_16.multNormal(vec3f, null);

		float[] expected = {6, 18, 30};

		assertVectorEquals(expected, rotated_normal_vec);
	}

	@Test
	public void testMultVector3fNormalWithItselfAsStore() {
		Vector3f rotated_normal_vec = m1_16.multNormal(vec3f, vec3f);

		float[] expected = {6, 18, 30};

		assertVectorEquals(expected, vec3f);
		assertVectorEquals(expected, rotated_normal_vec);
		assertSame(rotated_normal_vec, vec3f);
	}

	@Test
	public void testMultVector3fNormalAcross() {
		Vector3f store = new Vector3f();
		Vector3f rotated_normal_vec = m1_16.multNormalAcross(vec3f, store);

		float[] expected = {15, 18, 21};

		assertVectorEquals(expected, store);
		assertVectorEquals(expected, rotated_normal_vec);
		assertSame(rotated_normal_vec, store);
	}

	@Test
	public void testMultVector3fNormalAcrossNullStore() {
		Vector3f rotated_normal_vec = m1_16.multNormalAcross(vec3f, null);

		float[] expected = {15, 18, 21};

		assertVectorEquals(expected, rotated_normal_vec);
	}

	@Test
	public void testMultVector3fNormalAcrossWithItselfAsStore() {
		Vector3f rotated_normal_vec = m1_16.multNormalAcross(vec3f, vec3f);

		float[] expected = {15, 18, 21};

		assertVectorEquals(expected, vec3f);
		assertVectorEquals(expected, rotated_normal_vec);
		assertSame(rotated_normal_vec, vec3f);
	}

	@Test
	public void testMultVector4f() {
		Vector4f rotated_vec = m1_16.mult(vec4f);

		float[] expected = {10, 26, 42, 58};

		assertVectorEquals(expected, rotated_vec);
	}

	@Test
	public void testMultVector4fWithStore() {
		Vector4f store = new Vector4f();
		Vector4f rotated_vec = m1_16.mult(vec4f, store);

		float[] expected = {10, 26, 42, 58};

		assertVectorEquals(expected, rotated_vec);
		assertVectorEquals(expected, store);
		assertSame(rotated_vec, store);
	}

	@Test
	public void testMultVector4fWithItselfAsStore() {
		Vector4f rotated_vec = m1_16.mult(vec4f, vec4f);

		float[] expected = {10, 26, 42, 58};

		assertVectorEquals(expected, rotated_vec);
		assertVectorEquals(expected, vec4f);
		assertSame(rotated_vec, vec4f);
	}

	@Test(expected=NullPointerException.class)
	public void testMultVector4fNullInput() {
		Vector4f null_vector = null;
		m1_16.mult(null_vector);
	}

	@Test
	public void testMultAcrossVector4f() {
		Vector4f rotated_vec = m1_16.multAcross(vec4f);

		float[] expected = {28, 32, 36, 40};

		assertVectorEquals(expected, rotated_vec);
	}

	@Test
	public void testMultAcrossVector4fWithStore() {
		Vector4f store = new Vector4f();
		Vector4f rotated_vec = m1_16.multAcross(vec4f, store);

		float[] expected = {28, 32, 36, 40};

		assertVectorEquals(expected, rotated_vec);
		assertVectorEquals(expected, store);
		assertSame(rotated_vec, store);
	}

	@Test
	public void testMultAcrossVector4fWithItselfAsStore() {
		Vector4f rotated_vec = m1_16.multAcross(vec4f, vec4f);

		float[] expected = {28, 32, 36, 40};

		assertVectorEquals(expected, rotated_vec);
		assertVectorEquals(expected, vec4f);
		assertSame(rotated_vec, vec4f);
	}

	@Test(expected=NullPointerException.class)
	public void testMultAcrossVector4fNullInput() {
		Vector4f null_vector = null;
		m1_16.multAcross(null_vector);
	}


	@Test
	public void testInvert() {
		Matrix4f inverted_matrix = invertible_matrix.invert();

		float[] expected = {
			1.5f, 0f, -.5f,	-1f,
			-1.75f, .5f, .25f, 1.5f,
			-2.25f, 0f, .75f, 2f,
			-1f, 0f, 0f, 1f
		};

		assertMatrixEquals(expected, inverted_matrix);
	}

	@Test(expected=ArithmeticException.class)
	public void testInvertNonInvertibleMatrix() {
		m1_16.invert();
	}

	@Test
	public void testInvertIdentitfy() {
		assertTrue(m.invert().isIdentity());
	}

	@Test
	public void testInvertTwice() {
		Matrix4f inverted_twice = invertible_matrix.invert().invert();
		assertEquals(invertible_matrix, inverted_twice);
	}

	@Test
	public void testInvertLocal() {
		invertible_matrix.invertLocal();

		float[] expected = {
			1.5f, 0f, -.5f,	-1f,
			-1.75f, .5f, .25f, 1.5f,
			-2.25f, 0f, .75f, 2f,
			-1f, 0f, 0f, 1f
		};

		assertMatrixEquals(expected, invertible_matrix);
	}

	@Test(expected=ArithmeticException.class)
	public void testInvertLocalNonInvertibleMatrix() {
		m1_16.invertLocal();
	}

	@Test
	public void testInvertLocalIdentitfy() {
		assertTrue(m.invertLocal().isIdentity());
	}

	@Test
	public void testInvertWithStore() {
		Matrix4f store = new Matrix4f();

		Matrix4f inverted_matrix = invertible_matrix.invert(store);

		float[] expected = {
			1.5f, 0f, -.5f,	-1f,
			-1.75f, .5f, .25f, 1.5f,
			-2.25f, 0f, .75f, 2f,
			-1f, 0f, 0f, 1f
		};

		float[] original = {
			3, 0, 2, -1,
			1, 2, 0, -2,
			1, 0, 2, -3,
			3, 0, 2, 0
		};

		assertMatrixEquals(expected, store);
		assertMatrixEquals(expected, inverted_matrix);
		assertMatrixEquals(original, invertible_matrix);
	}

	@Test
	public void testAdjoint() {
		Matrix4f adjoint = invertible_matrix.adjoint();

		float[] expected = {
				12, 0, -4, -8,
				-14, 4, 2, 12,
				-18, 0, 6, 16,
				-8, 0, 0, 8
		};

		assertMatrixEquals(expected, adjoint);
	}

	@Test
	public void testAdjointWithStore() {
		Matrix4f store = new Matrix4f();

		Matrix4f adjoint = invertible_matrix.adjoint(store);

		float[] expected = {
				12, 0, -4, -8,
				-14, 4, 2, 12,
				-18, 0, 6, 16,
				-8, 0, 0, 8
		};

		assertMatrixEquals(expected, store);
		assertMatrixEquals(expected, adjoint);
	}

	@Test
	public void testAdjointWithItselfAsStore() {
		Matrix4f adjoint = invertible_matrix.adjoint(invertible_matrix);

		float[] expected = {
				12, 0, -4, -8,
				-14, 4, 2, 12,
				-18, 0, 6, 16,
				-8, 0, 0, 8
		};

		assertMatrixEquals(expected, invertible_matrix);
		assertMatrixEquals(expected, adjoint);
	}

	@Test
	public void testDeterminant() {
		assertEquals(8, invertible_matrix.determinant(), 1e-8f);
	}

	@Test
	public void testDeterminantZero() {
		assertEquals(0, m1_16.determinant(), 1e-8f);
	}

	@Test
	public void testZero() {
		Matrix4f zero = m.zero();
		assertEquals(Matrix4f.ZERO, m);
		assertEquals(Matrix4f.ZERO, zero);
	}

	@Test
	public void testAdd() {
		Matrix4f added_matrix = m1_16.add(m);

		float[] expected = {
			2, 2, 3, 4,
			5, 7, 7, 8,
			9, 10, 12, 12,
			13, 14, 15, 17
		};

		assertMatrixEquals(expected, added_matrix);
	}

	@Test
	public void testAddLocal() {
		m1_16.addLocal(m);

		float[] expected = {
			2, 2, 3, 4,
			5, 7, 7, 8,
			9, 10, 12, 12,
			13, 14, 15, 17
		};

		assertMatrixEquals(expected, m1_16);
	}

	@Test
	public void testAngleRotation90Deg() {
		m.angleRotation(new Vector3f(90, 90, 90));

		float[] expected = {
			0, 0, 1, 0,
			0, 1, 0, 0,
			-1, 0, 0, 0,
			0, 0, 0, 1
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testAngleRotation0Deg() {
		m.angleRotation(new Vector3f(0, 0, 0));

		float[] expected = {
			1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testAngleRotation30Deg() {
		m.angleRotation(new Vector3f(30, 30, 30));

		// cos(30 ) & sin(30)
		float c = FastMath.sqrt(3) / 2;
		float s = 1/2f;

		float[] expected = {
			c*c, -s*c + c*s*s, s*s+c*s*c, 0,
			s*c, c*c+s*s*s, c*-s+s*s*c, 0,
			-s, c*s, c*c, 0,
			0, 0, 0, 1
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testAngleRotationDifferentDegrees() {
		m.angleRotation(new Vector3f(30, 45, 60));

		float sx = 1 / 2f;
		float cx = FastMath.sqrt(3) / 2;
		float sy = 1 / FastMath.sqrt(2);
		float cy = 1 / FastMath.sqrt(2);
		float sz = FastMath.sqrt(3) / 2;
		float cz = 1 / 2f;

		float[] expected = {
			cz*cy, -sz*cx+cz*sy*sx, sz*sx+cz*sy*cx, 0,
			sz*cy, cz*cx+sz*sy*sx, cz*-sx+sz*sy*cx, 0,
			-sy, cy*sx, cy*cx, 0,
			0, 0, 0, 1
		};

		assertMatrixEquals(expected, m);
	}

	@Test
	public void testEquals() {
		Matrix4f m1 = new Matrix4f();
		Matrix4f m2 = new Matrix4f();
		assertEquals(m1, m2);
	}

	@Test
	public void testEqualsDifferentMatrices() {
		assertNotEquals(m, m1_16);
	}

	@Test
	public void testEqualsSameObject() {
		assertEquals(m, m);
	}

	@Test
	public void testEqualsNotMatrixObject() {
		assertNotEquals(m, new Object());
	}

	@Test
	public void testEqualsNull() {
		Matrix4f null_matrix = null;
		assertNotEquals(m, null_matrix);
	}

	@Test
	public void testClone() {
		Matrix4f cloned_matrix = m.clone();
		assertEquals(m, cloned_matrix);
		assertNotSame(m, invertible_matrix);
	}
}