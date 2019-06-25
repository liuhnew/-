/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jykj.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jykj.entity.Result;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.explorer.util.XmlUtil;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;

/**
 * @author Tijs Rademakers
 */
@RestController
@RequestMapping("/service")
public class ModelSaveRestResource implements ModelDataJsonConstants {
  
  protected static final Logger LOGGER = LoggerFactory.getLogger(ModelSaveRestResource.class);

  @Autowired
  private RepositoryService repositoryService;
  
  @Autowired
  private ObjectMapper objectMapper;
  
  @RequestMapping(value="/model/{modelId}/save", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.OK)
  public Result saveModel(@PathVariable String modelId, @RequestParam("name") String name,
                          @RequestParam("json_xml") String json_xml, @RequestParam("svg_xml") String svg_xml,
                          @RequestParam("description") String description) {
    try {
      
      Model model = repositoryService.getModel(modelId);
      
      ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
      
      modelJson.put(MODEL_NAME, name);
      modelJson.put(MODEL_DESCRIPTION, description);
      model.setMetaInfo(modelJson.toString());
      model.setName(name);
      
      repositoryService.saveModel(model);
      
      repositoryService.addModelEditorSource(model.getId(), json_xml.getBytes("utf-8"));

      
      InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
      TranscoderInput input = new TranscoderInput(svgStream);
      
      PNGTranscoder transcoder = new PNGTranscoder();
      // Setup output
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      TranscoderOutput output = new TranscoderOutput(outStream);
      
      // Do the transformation
      transcoder.transcode(input, output);
      final byte[] result = outStream.toByteArray();
      repositoryService.addModelEditorSourceExtra(model.getId(), result);
      System.out.println(result);
      return Result.createSuccess("保存成功");
      
    } catch (Exception e) {
      LOGGER.error("Error saving model", e);
      throw new ActivitiException("Error saving model", e);
    }
  }

  @RequestMapping(value="/model/save", method = RequestMethod.POST)
  public Result saveNativeModel() {
    try {
      String modelId = "1";
      String filePath = "/processes/hello.bpmn20.xml";
      InputStream is = Class.class.getClass().getResource(filePath).openStream();

      InputStreamReader in = new InputStreamReader(is, "UTF-8");

      XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
      XMLStreamReader xtr = xif.createXMLStreamReader(in);
      BpmnModel bpmnModel = (new BpmnXMLConverter()).convertToBpmnModel(xtr);

      ObjectNode modelNode = new BpmnJsonConverter().convertToJson(bpmnModel);
      repositoryService.addModelEditorSource(modelId, modelNode.toString().getBytes("utf-8"));
      xtr.close();
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (XMLStreamException s){
      s.printStackTrace();
    } finally {
    }
    return Result.createSuccess("success");
  }
}
