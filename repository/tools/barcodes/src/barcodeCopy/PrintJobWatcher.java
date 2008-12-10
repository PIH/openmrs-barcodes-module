package barcodeCopy;

import javax.print.DocPrintJob;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

public class PrintJobWatcher {
   
        // See  http://javaalmanac.com/egs/javax.print/WaitForDone.html?l=rel for source
        // true iff it is safe to close the print job's input stream
        private boolean done = false;
     
     
     
     
        /**
         * Create a PrintJobWatcher
         * @param print job to watch
         */
        public PrintJobWatcher(DocPrintJob job)
        {
            // Add a listener to the print job
            
            job.addPrintJobListener(new PrintJobAdapter()
            {
                public void printJobCanceled(PrintJobEvent pje) { System.out.println("MESSAGE " + pje.getPrintEventType());this.allDone();}
                public void printJobCompleted(PrintJobEvent pje) { System.out.println("MESSAGE " + pje.getPrintEventType());this.allDone(); }
                public void printJobFailed(PrintJobEvent pje) {System.out.println("MESSAGE " + pje.getPrintEventType());this.allDone(); }
                public void printJobNoMoreEvents(PrintJobEvent pje) { System.out.println("MESSAGE " + pje.getPrintEventType());this.allDone();}
     
                void allDone()
                {
                    synchronized (PrintJobWatcher.this)
                    {
                        done = true;
                        PrintJobWatcher.this.notify();
                    }
                }
            });
            
        }
     
     
     
        public synchronized void waitForDone()
        {
            try
            {
                while (!this.done)
                {
                    wait();
                }
            }
            catch (InterruptedException e)
            {
            }
        }
}
