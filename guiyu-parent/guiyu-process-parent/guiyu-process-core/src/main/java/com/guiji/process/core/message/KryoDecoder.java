package com.guiji.process.core.message;

import java.util.List;

import com.guiji.process.core.serialize.kryo.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class KryoDecoder extends ByteToMessageDecoder {
	
	@Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Object obj = KryoSerializer.deserialize(in);
        out.add(obj);
    }
	
}
