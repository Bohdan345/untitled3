import Pages.CRMPage;
import Pages.LoginPage;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static Pages.BasePage.destroy;
import static Utils.RandomData.getRandomInt;
import static Utils.RandomData.getRandomName;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class FunnelsTestCRM {

    @Test
    @Feature("Funnels")

    public void createFunnel() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        String name = getRandomName();

        new LoginPage()
                .login();
        CRMPage crmPage = new CRMPage()
                .goToCrm()
                .clickFunnelDropDown()
                .clickNewPiplineButton()
                .setNameInputModal(name)
                .clickConfirmModalButton()

                .waitLoader()
                .clickFunnelDropDown();


        assertThat(name,
                is(containsString(crmPage.getCurrentNameFunnel()))
        );

        destroy();
    }

    @Test
    @Feature("Funnels")
    public void deleteFunnel() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        new LoginPage()
                .login();
        CRMPage crmPage = new CRMPage()
                .goToCrm()
                .clickFunnelDropDown()
                .getFirstFunnel();
        String name = crmPage.getCurrentNameFunnel();

        crmPage.clickToolBarBtn()
                .clickDeleteFunnelButton()
                .clickConfirmModalButton()
                .clickFunnelDropDown();

        assertThat(name, not(containsString(crmPage.getCurrentNameFunnel())));

        destroy();

    }


    @Test
    @Feature("Funnels")
    public void renameFunnel() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        new LoginPage()

                .login();

        CRMPage crmPage = new CRMPage()
                .goToCrm()
                .clickFunnelDropDown()
                .getFirstFunnel();
        String name = crmPage.getCurrentNameFunnel();

        crmPage.clickToolBarBtn()
                .setFunnelNameToolBar("Rename" + getRandomInt(1, 100))
                .backToCRMPage()
                .waitInvisibleLoader();

        assertThat(name, not(containsString(crmPage.getCurrentNameFunnel())));
        destroy();
    }


}
