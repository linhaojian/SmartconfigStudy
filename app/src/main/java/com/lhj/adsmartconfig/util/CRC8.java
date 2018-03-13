package com.lhj.adsmartconfig.util;

import android.util.Log;

import com.espressif.iot.esptouch.util.ByteUtil;

import java.util.Arrays;
import java.util.zip.Checksum;

public class CRC8  {
	 byte crc8_init = 0;
	 byte[] crc8_tab = {
			 0, 49, 98, 83, (byte) 196, (byte) 245, (byte) 166, (byte) 151, (byte) 185, (byte) 136,
			 (byte) 219, (byte) 234, 125, 76, 31, 46, 67, 114, 33, 16, (byte) 135,
			 (byte) 182, (byte) 229, (byte) 212, (byte) 250, (byte) 203, (byte) 152, (byte) 169, 62, 15, 92, 109, (byte) 134, (byte) 183, (byte) 228, (byte) 213, 66, 115, 32, 17, 63,
			 14, 93, 108, (byte) 251, (byte) 202, (byte) 153, (byte) 168, (byte) 197, (byte) 244, (byte) 167, (byte) 150, 1, 48, 99, 82, 124, 77, 30, 47, (byte) 184, (byte) 137,
			 (byte) 218, (byte) 235, 61, 12, 95, 110, (byte) 249, (byte) 200, (byte) 155, (byte) 170, (byte) 132, (byte) 181, (byte) 230, (byte) 215, 64, 113, 34, 19, 126, 79,
			 28, 45, (byte) 186, (byte) 139, (byte) 216, (byte) 233, (byte) 199, (byte) 246, (byte) 165, (byte) 148, 3, 50, 97, 80, (byte) 187, (byte) 138, (byte) 217, (byte) 232, 127, 78, 29,
			 44, 2, 51, 96, 81, (byte) 198, (byte) 247, (byte) 164, (byte) 149, (byte) 248, (byte) 201, (byte) 154, (byte) 171, 60, 13, 94, 111, 65, 112, 35, 18,
			 (byte) 133, (byte) 180, (byte) 231, (byte) 214, 122, 75, 24, 41, (byte) 190, (byte) 143, (byte) 220, (byte) 237, (byte) 195, (byte) 242, (byte) 161, (byte) 144, 7, 54, 101, 84,
			 57, 8, 91, 106, (byte) 253, (byte) 204, (byte) 159, (byte) 174, (byte) 128, (byte) 177, (byte) 226, (byte) 211, 68, 117, 38, 23, (byte) 252, (byte) 205, (byte) 158, (byte) 175,
			 56, 9, 90, 107, 69, 116, 39, 22, (byte) 129, (byte) 176, (byte) 227, (byte) 210, (byte) 191, (byte) 142, (byte) 221, (byte) 236, 123, 74, 25, 40, 6, 55,
			 100, 85, (byte) 194, (byte) 243, (byte) 160, (byte) 145, 71, 118, 37, 20, (byte) 131, (byte) 178, (byte) 225, (byte) 208, (byte) 254, (byte) 207, (byte) 156, (byte) 173, 58, 11,
			 88, 105, 4, 53, 102, 87, (byte) 192, (byte) 241, (byte) 162, (byte) 147, (byte) 189, (byte) 140, (byte) 223, (byte) 238, 121, 72, 27, 42, (byte) 193, (byte) 240,
			 (byte) 163, (byte) 146, 5, 52, 103, 86, 120, 73, 26, 43, (byte) 188, (byte) 141, (byte) 222, (byte) 239, (byte) 130, (byte) 179, (byte) 224, (byte) 209, 70, 119, 36,
			 21, 59, 10, 89, 104, (byte) 255, (byte) 206, (byte) 157, (byte) 172};

	/**
	 * 计算数组的CRC8校验值
	 *
	 * @param data
	 *            需要计算的数组
	 * @return CRC8校验值
	 */
	public byte calcCrc8(byte[] data) {
		return calcCrc8(data, 0, data.length, crc8_init);
	}

	/**
	 * 计算CRC8校验值
	 *
	 * @param data
	 *            数据
	 * @param offset
	 *            起始位置
	 * @param len
	 *            长度
	 * @return 校验值
	 */
	public byte calcCrc8(byte[] data, int offset, int len) {
		return calcCrc8(data, offset, len, crc8_init);
	}

	/**
	 * 计算CRC8校验值
	 *
	 * @param data
	 *            数据
	 * @param offset
	 *            起始位置
	 * @param len
	 *            长度
	 * @param preval
	 *            之前的校验值
	 * @return 校验值
	 */
	public byte calcCrc8(byte[] data, int offset, int len, byte preval) {
		byte ret = preval;
		for (int i = offset; i < (offset + len); ++i) {
			ret = crc8_tab[(0x00ff & (ret ^ data[i]))];
		}
		return ret;
	}

}
