package agh.alleviation.controller.edit_dialog;

import agh.alleviation.model.Seance;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/EditSeanceDialog.fxml")
public class EditSeanceDialogController extends EditDialogController<Seance> {
}
