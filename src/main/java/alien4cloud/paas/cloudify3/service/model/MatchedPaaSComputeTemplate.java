package alien4cloud.paas.cloudify3.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import alien4cloud.paas.model.PaaSNodeTemplate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateField")
public class MatchedPaaSComputeTemplate implements IMatchedPaaSTemplate {

    private PaaSNodeTemplate paaSNodeTemplate;

    private PaaSComputeTemplate paaSComputeTemplate;
}
