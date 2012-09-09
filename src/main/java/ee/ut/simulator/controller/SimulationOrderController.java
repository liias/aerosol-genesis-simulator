package ee.ut.simulator.controller;

import ee.ut.simulator.service.SimulationOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class SimulationOrderController {

    @Autowired
    private SimulationOrderService simulationOrderService;

    @RequestMapping("/order")
    public String order(Map<String, Object> map) {
        simulationOrderService.generateDefaultParameters();
        map.put("parameters", simulationOrderService.getDefaultParameters().getParameters());
        return "simulation/order";
    }
}
