package remotedeploy.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import remotedeploy.dialog.ConfigDialog;
import remotedeploy.dialog.FileChangeWatcherThread;
import remotedeploy.dialog.SelectFileUtil;
import remotedeploy.dialog.SshCopyUtil;

public class StartAction implements IObjectActionDelegate {

	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public StartAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
//		MessageDialog.openInformation(
//			shell,
//			"Remotedeploy",
//			"New Action was executed.");
		new ConfigDialog(shell).open();
		String retCode = SshCopyUtil.executeCommand();
		if(SelectFileUtil.getSelectFile().isDirectory()){
			FileChangeWatcherThread.start(SelectFileUtil.getSelectFile().getPath());
		}
		MessageDialog.openInformation(shell,"Remotedeploy",retCode);
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
