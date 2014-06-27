/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package photodb.io;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ssch
 */
public class ByteChannelTest {
    
    public ByteChannelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testTransferFromByteChannel() throws Exception {
        System.out.println("testTransferFromByteChannel");
        File dst = new File("/tmp/testTransferFromByteChannel.txt");
        byte source[] = new byte[]{41, 42, 43, 44, 45, 46, 47, 48, 49, 50};
        ByteChannel src = new ByteChannel(source);
        if(dst.exists()) {
            dst.delete();
        }
        if(dst.createNewFile()) {
            FileChannel fc = new FileOutputStream(dst).getChannel();
            fc.transferFrom(src, 0, source.length);
        }
        dst.deleteOnExit();
    }
    
    /**
     * Test of read method, of class ByteChannel.
     */
    @Test
    public void testRead_ByteBuffer() throws Exception {
        System.out.println("read");
        ByteBuffer dst = null;
        ByteChannel instance = null;
        int expResult = 0;
        int result = instance.read(dst);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class ByteChannel.
     */
    @Test
    public void testRead_3args() throws Exception {
        System.out.println("read");
        ByteBuffer[] dsts = null;
        int offset = 0;
        int length = 0;
        ByteChannel instance = null;
        long expResult = 0L;
        long result = instance.read(dsts, offset, length);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of position method, of class ByteChannel.
     */
    @Test
    public void testPosition_0args() throws Exception {
        System.out.println("position");
        ByteChannel instance = null;
        long expResult = 0L;
        long result = instance.position();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of position method, of class ByteChannel.
     */
    @Test
    public void testPosition_long() throws Exception {
        System.out.println("position");
        long newPosition = 0L;
        ByteChannel instance = null;
        FileChannel expResult = null;
        FileChannel result = instance.position(newPosition);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class ByteChannel.
     */
    @Test
    public void testSize() throws Exception {
        System.out.println("size");
        ByteChannel instance = null;
        long expResult = 0L;
        long result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of truncate method, of class ByteChannel.
     */
    @Test
    public void testTruncate() throws Exception {
        System.out.println("truncate");
        long size = 0L;
        ByteChannel instance = null;
        FileChannel expResult = null;
        FileChannel result = instance.truncate(size);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of force method, of class ByteChannel.
     */
    @Test
    public void testForce() throws Exception {
        System.out.println("force");
        boolean metaData = false;
        ByteChannel instance = null;
        instance.force(metaData);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transferTo method, of class ByteChannel.
     */
    @Test
    public void testTransferTo() throws Exception {
        System.out.println("transferTo");
        long position = 0L;
        long count = 0L;
        WritableByteChannel target = null;
        ByteChannel instance = null;
        long expResult = 0L;
        long result = instance.transferTo(position, count, target);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transferFrom method, of class ByteChannel.
     */
    @Test
    public void testTransferFrom() throws Exception {
        System.out.println("transferFrom");
        ReadableByteChannel src = null;
        long position = 0L;
        long count = 0L;
        ByteChannel instance = null;
        long expResult = 0L;
        long result = instance.transferFrom(src, position, count);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class ByteChannel.
     */
    @Test
    public void testRead_ByteBuffer_long() throws Exception {
        System.out.println("read");
        ByteBuffer dst = null;
        long position = 0L;
        ByteChannel instance = null;
        int expResult = 0;
        int result = instance.read(dst, position);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class ByteChannel.
     */
    @Test
    public void testWrite_ByteBuffer_long() throws Exception {
        System.out.println("write");
        ByteBuffer src = null;
        long position = 0L;
        ByteChannel instance = null;
        int expResult = 0;
        int result = instance.write(src, position);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of map method, of class ByteChannel.
     */
    @Test
    public void testMap() throws Exception {
        System.out.println("map");
        FileChannel.MapMode mode = null;
        long position = 0L;
        long size = 0L;
        ByteChannel instance = null;
        MappedByteBuffer expResult = null;
        MappedByteBuffer result = instance.map(mode, position, size);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lock method, of class ByteChannel.
     */
    @Test
    public void testLock() throws Exception {
        System.out.println("lock");
        long position = 0L;
        long size = 0L;
        boolean shared = false;
        ByteChannel instance = null;
        FileLock expResult = null;
        FileLock result = instance.lock(position, size, shared);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tryLock method, of class ByteChannel.
     */
    @Test
    public void testTryLock() throws Exception {
        System.out.println("tryLock");
        long position = 0L;
        long size = 0L;
        boolean shared = false;
        ByteChannel instance = null;
        FileLock expResult = null;
        FileLock result = instance.tryLock(position, size, shared);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of implCloseChannel method, of class ByteChannel.
     */
    @Test
    public void testImplCloseChannel() throws Exception {
        System.out.println("implCloseChannel");
        ByteChannel instance = null;
        instance.implCloseChannel();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class ByteChannel.
     */
    @Test
    public void testWrite_ByteBuffer() throws Exception {
        System.out.println("write");
        ByteBuffer src = null;
        ByteChannel instance = null;
        int expResult = 0;
        int result = instance.write(src);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class ByteChannel.
     */
    @Test
    public void testWrite_3args() throws Exception {
        System.out.println("write");
        ByteBuffer[] srcs = null;
        int offset = 0;
        int length = 0;
        ByteChannel instance = null;
        long expResult = 0L;
        long result = instance.write(srcs, offset, length);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
