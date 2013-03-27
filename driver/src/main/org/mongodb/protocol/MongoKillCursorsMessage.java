/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.protocol;

import org.mongodb.io.ChannelAwareOutputBuffer;
import org.mongodb.operation.MongoKillCursor;
import org.mongodb.result.ServerCursor;

public class MongoKillCursorsMessage extends MongoRequestMessage {
    private final MongoKillCursor killCursor;

    public MongoKillCursorsMessage(final MongoKillCursor killCursor) {
        super(OpCode.OP_KILL_CURSORS);
        this.killCursor = killCursor;
    }

    @Override
    protected void serializeMessageBody(final ChannelAwareOutputBuffer buffer) {
        writeKillCursorsPrologue(killCursor.getServerCursors().size(), buffer);
        for (final ServerCursor curServerCursor : killCursor.getServerCursors()) {
            buffer.writeLong(curServerCursor.getId());
        }
    }

    private void writeKillCursorsPrologue(final int numCursors, final ChannelAwareOutputBuffer buffer) {
        buffer.writeInt(0); // reserved
        buffer.writeInt(numCursors);
    }
}