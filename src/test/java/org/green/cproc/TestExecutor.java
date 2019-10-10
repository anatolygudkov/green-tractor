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

import java.util.List;

public class TestExecutor extends DefaultExecutor<TestEntry, TestProcessListener> {

    public interface Listener {

        void onTestEntryAProcessed();

        void onTestEntryBProcessed();

        void onStartExecuted();

        void onStopExecuted();

        void onTestCommandAExecuted();

        void onTestCommandBExecuted();
    }

    private final Listener listener;

    public TestExecutor(final Listener listener) {
        super("Test executor");
        this.listener = listener;
    }

    @Override
    public void processEntry(final TestEntry entry) {
        if (entry instanceof TestEntryA) {
            listener.onTestEntryAProcessed();
            return;
        }
        if (entry instanceof TestEntryB) {
            listener.onTestEntryBProcessed();
            return;
        }
        throw new IllegalArgumentException("Unknown entry: " + entry);
    }

    @Override
    protected void doStart() {
        listener.onStartExecuted();
    }

    @Override
    protected void doStop() {
        listener.onStopExecuted();
    }

    @Override
    protected void doCustom(final Command command, final List<TestProcessListener> listeners) {
        if (command instanceof TestCommandA) {
            final TestCommandA testCommandA = (TestCommandA) command;

            listener.onTestCommandAExecuted();

            for (int i = 0; i < listeners.size(); i++) {
                try {
                    listeners.get(i).onTestCommandA(this, testCommandA.result());
                } catch (final Exception e) {
                    errorHandler.onError(this, "An error while onTestCommandA notification: " +
                            e.getLocalizedMessage(), e);
                }
            }
            return;
        }

        if (command instanceof TestCommandB) {
            final TestCommandB testCommandB = (TestCommandB) command;

            listener.onTestCommandBExecuted();

            for (int i = 0; i < listeners.size(); i++) {
                try {
                    listeners.get(i).onTestCommandB(this, testCommandB.result());
                } catch (final Exception e) {
                    errorHandler.onError(this, "An error while onTestCommandB notification: " +
                            e.getLocalizedMessage(), e);
                }
            }
            return;
        }

        throw new UnsupportedOperationException("Unknown command: " + command);
    }
}