package com.seedfinding.mccore.net;

import java.io.IOException;

public interface IByteSerializable {

	void read(ByteBuffer buffer) throws IOException;

	void write(ByteBuffer buffer) throws IOException;

}
