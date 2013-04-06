package remotedeploy.dialog;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

public class SelectFileUtil {

	public static java.io.File getSelectFile() {
		IStructuredSelection structured = (IStructuredSelection) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService()
				.getSelection("org.eclipse.jdt.ui.PackageExplorer");

		Object selected = structured.getFirstElement();

		if (selected instanceof File) {

			File file = (File) selected;

			return file.getLocation().toFile();

		} else if (selected instanceof Folder) {

			Folder folder = (Folder) selected;

			return  folder.getLocation().toFile();

		}
		return null;

	}
}
