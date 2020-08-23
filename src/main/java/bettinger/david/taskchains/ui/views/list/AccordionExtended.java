package bettinger.david.taskchains.ui.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.dom.Element;

public class AccordionExtended extends Accordion {


    public AccordionPanel addAt(String summary, Component content, int index){
        AccordionPanel accordionPanel = new AccordionPanel(summary, content);
        return addAt(accordionPanel, index);
    }

    public AccordionPanel addAt(AccordionPanel accordionPanel, int index){
        if (getElement().getChildCount() <= index){
            return add(accordionPanel);
        }

        Element tempToReplace;
        Element tempToInsert = accordionPanel.getElement();

        while (index < getElement().getChildCount()){
            tempToReplace = getElement().getChild(index);
            getElement().setChild(index, tempToInsert);
            tempToInsert = tempToReplace;
            index ++;
        }

        getElement().setChild(index, tempToInsert);

        return accordionPanel;
    }
}
