/**
 * MIT License
 * <p>
 * Copyright (c) 2019 Anatoly Gudkov
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.green.cproc;

public interface ConcurrentProcess<E extends Entry, X extends Executor<E>, L extends ConcurrentProcessListener<E, X>>
        extends AutoCloseable {

    <EE extends E> EntrySender<EE> newEntrySender(Class<EE> classOfEntry);

    Execution<ListenerResult> addListener(L listener) throws ConcurrentProcessClosedException, InterruptedException;

    Execution<ListenerResult> removeListener(L listener) throws ConcurrentProcessClosedException, InterruptedException;

    Execution<VoidResult> start() throws ConcurrentProcessClosedException, InterruptedException;

    Execution<VoidResult> stop() throws ConcurrentProcessClosedException, InterruptedException;

}