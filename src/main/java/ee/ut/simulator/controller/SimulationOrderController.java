package ee.ut.simulator.controller;

import ee.ut.simulator.domain.simulation.SimulationOrder;
import ee.ut.simulator.service.simulation.SimulationOrderService;
import ee.ut.simulator.service.simulation.SimulationProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/order")
public class SimulationOrderController {

    @Autowired
    private SimulationOrderService simulationOrderService;

    @Autowired
    private SimulationProcessService simulationProcessService;

    @Valid
    @ModelAttribute("simulationOrder")
    public SimulationOrder getSimulationOrder() {
        return simulationOrderService.getNewOrder();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String edit(@ModelAttribute("simulationOrder") SimulationOrder simulationOrder) {
        return "simulation/order";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String submit(@ModelAttribute("simulationOrder") SimulationOrder simulationOrder, BindingResult result) {
        if (result.hasErrors()) {
            return "simulation/order";
        } else {
            simulationOrderService.add(simulationOrder);
            return "redirect:/order";
        }
    }

    @RequestMapping("/orderlist/{orderId}")
    public String listProcessesForOrder(@PathVariable("orderId") long orderId) {
        simulationOrderService.listProcesses();

        //map.put("user", new User());
        //map.put("userList", userService.listUsers());

        return "user";
    }


    //TODO: Should probably be in process controller
    @RequestMapping("/processStart/{processId}")
    public String startProcess(@PathVariable("processId") long processId) {
        simulationProcessService.start(processId);

        //map.put("user", new User());
        //map.put("userList", userService.listUsers());

        return "redirect:/order";
    }
}
